package io.vincent.learning.stack.design.patterns.filterchain.v4;

public class ValidationHandler implements EnhancedHandler {
    @Override
    public boolean shouldExecute(ChainContext ctx) {
        return ctx.getRequest().getContentType().equals("application/json");
    }

    @Override
    public void handle(ChainContext ctx, Chain chain) {
//        JsonObject body = parseJson(ctx.getRequestBody());
//        if (!body.validate()) {
//            ctx.terminateWithError(400, "Invalid request format");
//            return;
//        }
        chain.proceed(ctx);
    }
    
    @Override
    public void onError(ChainContext ctx, Throwable ex) {
//        ctx.logError("Validation failed", ex);
//        super.onError(ctx, ex);
    }
}