package io.vincent.learning.stack.design.patterns.filterchain.v4;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// 默认实现
public class DefaultRouteResolver implements RouteResolver {
    private final List<Route> routes = new CopyOnWriteArrayList<>();

    @Override
    public Route resolve(ChainContext ctx) {
        return routes.stream()
            .filter(r -> r.matches(ctx))
            .sorted(Comparator.comparingInt(Route::getOrder))
            .findFirst()
            .orElse(null);
    }

    @Override
    public void addRoute(Route route) {
        routes.add(route);
        routes.sort(Comparator.comparingInt(Route::getOrder));
    }

    @Override
    public List<Route> getRoutes() {
        return routes;
    }
}