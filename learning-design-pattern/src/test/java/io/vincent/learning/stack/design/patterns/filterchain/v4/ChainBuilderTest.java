package io.vincent.learning.stack.design.patterns.filterchain.v4;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class ChainBuilderTest {
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;

    @BeforeEach
    void setup() {
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
    }

    // 测试用例1：基础流程验证
    @Test
    void should_execute_handlers_in_order() {
        // 准备
        List<String> executionOrder = new ArrayList<>();
        EnhancedHandler h1 = (ctx, chain) -> {
            executionOrder.add("A");
            chain.proceed(ctx);
        };
        EnhancedHandler h2 = (ctx, chain) -> executionOrder.add("B");

        // 执行
        Chain chain = new ChainBuilder()
                .addHandler(h1)
                .addHandler(h2)
                .build();
        chain.proceed(new ChainContext(mockRequest, mockResponse));

        // 验证
        assertThat(executionOrder).containsExactly("A", "B");
    }

    // 测试用例2：条件处理器验证
    @Test
    void should_skip_conditional_handler() {
        // 准备
        EnhancedHandler mockHandler = mock(EnhancedHandler.class);
        Chain chain = new ChainBuilder()
                .when(ctx -> false, mockHandler)
                .build();

        // 执行
        chain.proceed(new ChainContext(mockRequest, mockResponse));

        // 验证
        verify(mockHandler, never()).handle(any(), any());
    }

    // 测试用例3：异步处理器验证
    @Test
    void should_execute_in_async_thread() throws Exception {
        // 准备
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        AtomicReference<String> threadName = new AtomicReference<>();

        Chain chain = new ChainBuilder()
                .async((ctx, ch) -> threadName.set(Thread.currentThread().getName()), singleThreadExecutor)
                .build();

        // 执行
        CompletionStage<Void> future = chain.proceedAsync(new ChainContext(mockRequest, mockResponse));
        future.toCompletableFuture().get(1, TimeUnit.SECONDS);

        // 验证
        assertThat(threadName.get()).contains("pool-");
        singleThreadExecutor.shutdown();
    }

    // 测试用例4：排序策略验证
    @Test
    void should_sort_handlers_correctly() {
        // 准备
        EnhancedHandler h1 = (ctx, chain) -> ctx.setAttribute("order", 1);
        EnhancedHandler h2 = (ctx, chain) -> ctx.setAttribute("order", 2);

        // 倒序排序
        Chain chain = new ChainBuilder()
                .addHandler(h1)
                .addHandler(h2)
                .sort((a, b) -> -1) // 反转顺序
                .build();

        // 执行
        ChainContext ctx = new ChainContext(mockRequest, mockResponse);
        chain.proceed(ctx);

        // 验证：最后执行的处理器结果会覆盖之前的值
        assertThat(ctx.getAttribute("order")).isEqualTo(2);
    }

    // 测试用例5：链隔离性验证
    @Test
    void should_maintain_chain_isolation() {
        // 准备基础链
        Chain baseChain = new ChainBuilder()
                .addHandler((ctx, chain) -> ctx.setAttribute("base", true))
                .build();

        // 创建扩展链
        Chain extendedChain = new ChainBuilder()
                .addHandler((ctx, chain) -> ctx.setAttribute("base", true))
                .addHandler((ctx, chain) -> ctx.setAttribute("extended", true))
                .build();

        // 执行测试
        ChainContext baseCtx = new ChainContext(mockRequest, mockResponse);
        baseChain.proceed(baseCtx);

        ChainContext extendedCtx = new ChainContext(mockRequest, mockResponse);
        extendedChain.proceed(extendedCtx);

        // 验证隔离性
        assertThat(baseCtx.getAttribute("extended")).isNull();
        assertThat(extendedCtx.getAttribute("base")).isNotNull();
    }

    // 测试用例6：异常配置检测
    @Test
    void should_throw_on_invalid_config() {
        // 空处理器检测
        assertThatThrownBy(() -> new ChainBuilder().addHandler(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("cannot be null");

        // 无效线程池检测
        assertThatThrownBy(() -> new ChainBuilder().withAsyncExecutor(null))
                .isInstanceOf(NullPointerException.class);
    }

    // 测试用例7：条件处理器优先级验证
    @Test
    void should_prioritize_conditional_handlers() {
        // 准备冲突条件
        EnhancedHandler defaultHandler = (ctx, chain) -> ctx.setAttribute("result", "default");
        EnhancedHandler specialHandler = (ctx, chain) -> ctx.setAttribute("result", "special");

        Chain chain = new ChainBuilder()
                .when(ctx -> true, specialHandler)
                .addHandler(defaultHandler)
                .build();

        // 执行
        ChainContext ctx = new ChainContext(mockRequest, mockResponse);
        chain.proceed(ctx);

        // 验证条件处理器优先执行
        assertThat(ctx.getAttribute("result")).isEqualTo("special");
    }
}