package io.vincent.learning.stack.design.patterns.filterchain.v4;

import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

public class PathPatternRoute implements Route {
    private final PathPattern pattern;
    private final String handlerName;
    private final int order;

    public PathPatternRoute(String pathPattern, String handlerName, int order) {
        this.pattern = PathPatternParser.defaultInstance.parse(pathPattern);
        this.handlerName = handlerName;
        this.order = order;
    }

    @Override
    public boolean matches(ChainContext ctx) {
        return pattern.matches(PathContainer.parsePath(ctx.getRequest().getRequestURI()));
    }

    @Override
    public void apply(ChainContext ctx) {
        PathPattern.PathMatchInfo matchInfo = pattern.matchAndExtract(
            PathContainer.parsePath(ctx.getRequest().getRequestURI()));
//        ctx.setAttribute("pathParams", matchInfo.getUriVariables());
        ctx.setAttribute("handler", handlerName);
    }

    @Override
    public int getOrder() {
        return order;
    }
}