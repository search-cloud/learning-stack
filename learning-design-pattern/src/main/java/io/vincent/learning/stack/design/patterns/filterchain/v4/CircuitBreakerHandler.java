//package io.vincent.learning.stack.design.patterns.filterchain.v4;
//
//public class CircuitBreakerHandler implements EnhancedHandler {
//    private final CircuitBreaker breaker = CircuitBreaker.ofDefaults();
//
//    @Override
//    public void handle(ChainContext ctx, Chain chain) {
//        breaker.execute(() -> {
//            chain.proceed(ctx);
//            return null;
//        });
//    }
//
//    @Override
//    public void onError(ChainContext ctx, Throwable ex) {
//        if (ex instanceof CircuitBreakerOpenException) {
//            ctx.fallbackToCache();
//        }
//        super.onError(ctx, ex);
//    }
//}