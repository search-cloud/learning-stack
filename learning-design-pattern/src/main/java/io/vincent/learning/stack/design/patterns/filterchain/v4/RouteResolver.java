package io.vincent.learning.stack.design.patterns.filterchain.v4;

import java.util.List;

public interface RouteResolver {
    /**
     * 解析当前上下文对应的路由
     * @return 匹配的路由（可能为null）
     */
    Route resolve(ChainContext ctx);

    /**
     * 注册路由规则
     */
    void addRoute(Route route);

    /**
     * 路由表快照
     */
    List<Route> getRoutes();
}

