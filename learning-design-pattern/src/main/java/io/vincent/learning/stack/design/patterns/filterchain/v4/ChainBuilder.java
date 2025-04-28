package io.vincent.learning.stack.design.patterns.filterchain.v4;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Predicate;

/**
 * 责任链构建器（支持流式API和动态编排）
 */
public class ChainBuilder {
    // 处理器容器（线程安全）
    private final List<EnhancedHandler> handlers = new CopyOnWriteArrayList<>();
    
    // 异步执行器（可配置）
    private Executor asyncExecutor = ForkJoinPool.commonPool();
    
    // 排序策略（默认插入顺序）
    private Comparator<EnhancedHandler> sorter = Comparator.comparingInt(handlers::indexOf);

    /**
     * 添加普通处理器
     */
    public ChainBuilder addHandler(EnhancedHandler handler) {
        handlers.add(Objects.requireNonNull(handler, "Handler cannot be null"));
        return this;
    }

    /**
     * 添加条件处理器（满足条件时执行）
     */
    public ChainBuilder when(Predicate<ChainContext> condition, EnhancedHandler handler) {
        return addHandler(new ConditionalHandler(condition, handler));
    }

    /**
     * 配置异步处理器（使用指定线程池）
     */
    public ChainBuilder async(EnhancedHandler handler, Executor executor) {
        return addHandler(new AsyncHandlerWrapper(handler, executor));
    }

    /**
     * 配置异步处理器（使用默认线程池）
     */
    public ChainBuilder async(EnhancedHandler handler) {
        return async(handler, this.asyncExecutor);
    }

    /**
     * 设置全局异步执行器
     */
    public ChainBuilder withAsyncExecutor(Executor executor) {
        this.asyncExecutor = Objects.requireNonNull(executor);
        return this;
    }

    /**
     * 设置自定义排序策略
     */
    public ChainBuilder sort(Comparator<EnhancedHandler> sorter) {
        this.sorter = Objects.requireNonNull(sorter);
        return this;
    }

    /**
     * 构建不可变的责任链实例
     */
    public Chain build() {
        // 应用排序策略
        List<EnhancedHandler> sortedHandlers = new ArrayList<>(handlers);
        sortedHandlers.sort(sorter);
        
        // 创建隔离的链实例
        return new DynamicChain(new LinkedList<>(sortedHandlers));
    }

    /**
     * 创建当前配置的副本
     */
    public ChainBuilder copy() {
        ChainBuilder copy = new ChainBuilder();
        copy.handlers.addAll(this.handlers);
        copy.asyncExecutor = this.asyncExecutor;
        copy.sorter = this.sorter;
        return copy;
    }
}
