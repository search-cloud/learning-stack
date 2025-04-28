package io.vincent.learning.stack.design.patterns.filterchain.v4;

// 动态路由处理器
public class RoutingHandler implements EnhancedHandler {

    private final RouteResolver routeResolver;

    public RoutingHandler(RouteResolver routeResolver) {
        this.routeResolver = routeResolver;
    }

    @Override
    public boolean shouldExecute(ChainContext ctx) {
        return routeResolver.resolve(ctx) != null;
    }

    @Override
    public void handle(ChainContext ctx, Chain chain) {
        Route route = routeResolver.resolve(ctx);
        if (route != null) {
            route.apply(ctx);
            chain.proceed(ctx);
        }
        ctx.setAttribute("route", route);

    }
}
