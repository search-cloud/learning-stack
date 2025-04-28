package io.vincent.learning.stack.design.patterns.filterchain.v4;

import com.google.common.util.concurrent.RateLimiter;

import java.io.IOException;

public class RateLimiterHandler implements EnhancedHandler {
    private final RateLimiter limiter = RateLimiter.create(100);

    @Override
    public void handle(ChainContext ctx, Chain chain) {
        if (limiter.tryAcquire()) {
            chain.proceed(ctx);
        } else {
            try {
                ctx.getResponse().sendError(429);
            } catch (IOException e) {
                throw new ChainException("Rate sendError", e);
            }
        }
    }
}