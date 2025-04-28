package io.vincent.learning.stack.design.patterns.filterchain.v4;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public interface Chain {
    /**
     * 继续执行责任链的下一个处理器
     * @param ctx 链式上下文对象
     */
    void proceed(ChainContext ctx);

    /**
     * 异步执行入口（可选）
     * @param ctx 上下文对象
     * @return 异步执行结果
     */
    default CompletionStage<Void> proceedAsync(ChainContext ctx) {
        return CompletableFuture.runAsync(() -> proceed(ctx));
    }
}