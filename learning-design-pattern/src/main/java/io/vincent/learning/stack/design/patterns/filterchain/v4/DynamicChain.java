package io.vincent.learning.stack.design.patterns.filterchain.v4;

import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DynamicChain implements Chain {

    // 使用并发安全的队列存储处理器（深度拷贝保障线程安全）
    private final Queue<EnhancedHandler> handlerQueue;

    // 上下文原型（用于创建每个请求的独立上下文）
    private final ChainContext contextPrototype;

    // 构造方法（保障不可变状态）
    public DynamicChain(Queue<EnhancedHandler> handlers) {
        this.handlerQueue = new ConcurrentLinkedQueue<>(handlers);
        this.contextPrototype = new ChainContext();
    }

    @Override
    public void proceed(ChainContext ctx) {
        if (ctx.getTerminated().get() || handlerQueue.isEmpty()) {
            return;
        }

        EnhancedHandler current = handlerQueue.poll();
        try {
            ctx.setCurrentHandler(current);
            if (current.shouldExecute(ctx)) {
                current.handle(ctx, this);
            } else {
                proceed(ctx); // 自动跳过未满足条件的处理器
            }
        } catch (Exception ex) {
            current.onError(ctx, ex);
        }
    }

    @Override
    public CompletionStage<Void> proceedAsync(ChainContext ctx) {
        return CompletableFuture.supplyAsync(() -> {
            // 创建上下文副本（保障线程安全）
            ChainContext asyncCtx = ctx.copy();
            asyncCtx.mergeFrom(ctx);

            proceed(asyncCtx); // 执行同步流程

            // 回写异步上下文变更
            ctx.mergeFrom(asyncCtx);
            return null;
        });
    }
}