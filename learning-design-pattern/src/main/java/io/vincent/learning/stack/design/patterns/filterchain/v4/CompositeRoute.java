package io.vincent.learning.stack.design.patterns.filterchain.v4;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class CompositeRoute implements Route {
    private final List<Predicate<ChainContext>> predicates;
    private final Consumer<ChainContext> action;
    private final int order;

    public CompositeRoute(List<Predicate<ChainContext>> predicates, 
                         Consumer<ChainContext> action,
                         int order) {
        this.predicates = predicates;
        this.action = action;
        this.order = order;
    }

    @Override
    public boolean matches(ChainContext ctx) {
        return predicates.stream().allMatch(p -> p.test(ctx));
    }

    @Override
    public void apply(ChainContext ctx) {
        action.accept(ctx);
    }
}