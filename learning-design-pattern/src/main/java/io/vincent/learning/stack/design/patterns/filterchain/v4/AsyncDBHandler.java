package io.vincent.learning.stack.design.patterns.filterchain.v4;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncDBHandler implements EnhancedHandler {
    private final Executor dbExecutor = Executors.newWorkStealingPool();
    
    @Override
    public CompletionStage<Void> handleAsync(ChainContext ctx, Chain chain) {
        return CompletableFuture.supplyAsync(() -> {
//            User user = userDao.findById(ctx.getUserId());
            ctx.setAttribute("currentUser", "user");
            return null;
        }, dbExecutor).thenCompose(v -> chain.proceedAsync(ctx));
    }

    @Override
    public void handle(ChainContext ctx, Chain chain) {
        throw new UnsupportedOperationException("Sync mode not supported");
    }
}