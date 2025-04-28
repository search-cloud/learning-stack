package io.vincent.learning.stack.design.patterns.filterchain.v4;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

/**
 * 异步处理器包装类
 */
class AsyncHandlerWrapper implements EnhancedHandler {
    private final EnhancedHandler delegate;
    private final Executor executor;

    public AsyncHandlerWrapper(EnhancedHandler delegate, Executor executor) {
        this.delegate = delegate;
        this.executor = executor;
    }

    @Override
    public void handle(ChainContext ctx, Chain chain) {
        executor.execute(() -> delegate.handle(ctx, chain));
    }

    @Override
    public CompletionStage<Void> handleAsync(ChainContext ctx, Chain chain) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        executor.execute(() -> {
            try {
                delegate.handle(ctx, chain);
                future.complete(null);
            } catch (Exception ex) {
                future.completeExceptionally(ex);
            }
        });
        return future;
    }
}