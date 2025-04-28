//package io.vincent.learning.stack.design.patterns.filterchain.v4;
//
//public class ChainMain {
//    public static void main(String[] args) {
//        // 构建增强型处理链
//        Chain processingChain = new ChainBuilder()
//                .when(ctx -> !ctx.isAuthenticated(), new AuthenticationHandler())
//                .async(new RequestLoggingHandler())
//                .addHandler(new BodyParsingHandler())
//                .addHandler(new RateLimiterHandler())
//                .sort(AnnotationBasedSorter::new)
//                .build();
//
//// 执行处理流程
//        ChainContext context = new ChainContext(request, response);
//        processingChain.proceed(context);
//
//// 异步执行示例
//        processingChain.proceedAsync(context)
//                .exceptionally(ex -> {
//                    // 全局异常处理
//                    return null;
//                });
//    }
//}
