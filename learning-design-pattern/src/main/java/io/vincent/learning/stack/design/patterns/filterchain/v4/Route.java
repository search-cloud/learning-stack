package io.vincent.learning.stack.design.patterns.filterchain.v4;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * 路由规则抽象接口
 * <p>
 * 核心能力：
 * <l>
 * <li>1. 请求匹配判断</li>
 * <li>2. 参数解析与上下文增强</li>
 * <li>3. 路由元数据管理</li>
 * </l>
 * </p>
 */
public interface Route extends Serializable {
    /**
     * 判断当前路由是否匹配请求
     */
    boolean matches(ChainContext ctx);

    /**
     * 应用路由规则到上下文
     */
    void apply(ChainContext ctx);

    /**
     * 路由优先级（数值越小优先级越高）
     */
    default int getOrder() {
        return Integer.MAX_VALUE;
    }

    /**
     * 路由元数据（用于监控和调试）
     */
    default Map<String, Object> getMetadata() {
        return Collections.emptyMap();
    }
}