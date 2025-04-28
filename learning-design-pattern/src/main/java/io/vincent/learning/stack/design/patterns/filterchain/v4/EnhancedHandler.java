package io.vincent.learning.stack.design.patterns.filterchain.v4;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * 增强型处理器接口（支持同步/异步混合处理）
 * <p>
 * 设计原则：
 * 1. 开闭原则：通过组合扩展，而非修改已有实现
 * 2. 单一职责：每个处理器只关注单一功能点
 * 3. 控制反转：处理逻辑自主控制是否继续链路
 */
public interface EnhancedHandler {
    /**
     * 同步处理入口（必须实现）
     *
     * @param ctx   当前上下文对象
     * @param chain 责任链控制对象
     */
    void handle(ChainContext ctx, Chain chain);

    /**
     * 异步处理入口（默认实现）
     *
     * @return 异步执行阶段
     */
    default CompletionStage<Void> handleAsync(ChainContext ctx, Chain chain) {
        return CompletableFuture.runAsync(() -> handle(ctx, chain));
    }

    /**
     * 执行条件判断（动态路由基础）
     *
     * @return 是否执行当前处理器
     */
    default boolean shouldExecute(ChainContext ctx) {
        return true;
    }

    /**
     * 异常处理钩子（支持链式传递）
     *
     * @param ex 捕获的异常对象
     */
    default void onError(ChainContext ctx, Throwable ex) {
        ctx.setAttribute("LAST_ERROR", ex);
        throw new ChainException("Handler processing failed", ex);
    }

    /**
     * 后置处理钩子（响应式处理支持）
     */
    default void postHandle(ChainContext ctx) {
        // 默认空实现
    }

    // 新增资源管理方法
    default void init(ChainContext ctx) {

    }

    default void destroy(ChainContext ctx) {

    }

    /**
     * 版本控制支持
     */
    default String getVersion() {
        return "1.0.0";
    }
}