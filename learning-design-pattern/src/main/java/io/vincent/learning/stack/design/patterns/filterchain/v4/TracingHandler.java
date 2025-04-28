package io.vincent.learning.stack.design.patterns.filterchain.v4;

// 分布式追踪处理器
public class TracingHandler implements EnhancedHandler {
    @Override
    public void handle(ChainContext ctx, Chain chain) {
//        try (Scope scope = tracer.buildSpan("chain-process").startActive(true)) {
//            chain.proceed(ctx);
//        }
    }
}