package io.vincent.learning.stack.design.patterns.filterchain.v4;

import java.util.function.Predicate;

// 条件处理器包装类
class ConditionalHandler implements EnhancedHandler {
    private final Predicate<ChainContext> condition;
    private final EnhancedHandler delegate;

    public ConditionalHandler(Predicate<ChainContext> condition, EnhancedHandler delegate) {
        this.condition = condition;
        this.delegate = delegate;
    }

    @Override
    public boolean shouldExecute(ChainContext ctx) {
        return condition.test(ctx) && delegate.shouldExecute(ctx);
    }

    @Override
    public void handle(ChainContext ctx, Chain chain) {
        delegate.handle(ctx, chain);
    }
}