package io.vincent.learning.stack.design.patterns.filterchain.v4;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationHandler implements EnhancedHandler {
    @Override
    public boolean shouldExecute(ChainContext ctx) {
        return ctx.getAttribute("user") == null;
    }

    @Override
    public void handle(ChainContext ctx, Chain chain) {
        if (authenticate(ctx.request())) {
            ctx.setAttribute("user", "authenticated");
        }
        chain.proceed(ctx);
    }
    
    private boolean authenticate(HttpServletRequest req) {
        // 实际认证逻辑
        return true;
    }
}

