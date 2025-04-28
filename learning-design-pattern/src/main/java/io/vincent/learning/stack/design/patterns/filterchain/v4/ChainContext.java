package io.vincent.learning.stack.design.patterns.filterchain.v4;

import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChainContext {
    // 原始请求响应对象（假设在异步处理中仅用于元数据读取）
    @Getter
    private HttpServletRequest request;
    @Getter
    private HttpServletResponse response;
    
    // 可序列化的上下文属性（用于跨线程传递）
    private final ConcurrentMap<String, Serializable> attributes = new ConcurrentHashMap<>();

    // 流程控制标志（原子操作保证线程安全）
    @Getter
    private final AtomicBoolean terminated = new AtomicBoolean(false);

    // 元数据存储（Class类型为键）
    private final ConcurrentMap<Class<?>, Object> metadata = new ConcurrentHashMap<>();

    // 当前处理器追踪（用于调试）
    private volatile EnhancedHandler currentHandler;

    // 使用ThreadLocal存储临时变量
    private static final ThreadLocal<Map<String, Object>> threadLocals =
            ThreadLocal.withInitial(ConcurrentHashMap::new);

    public ChainContext() {
    }

    public ChainContext(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;

        // 自动提取关键请求信息到安全属性
//        this.attributes.put("requestURI", request.getRequestURI());
//        this.attributes.put("method", request.getMethod());
    }

    public <T extends Serializable> void setAttribute(String key, T attribute) {
        attributes.put(key, attribute);
    }

    public Object getAttribute(String obj) {
        return attributes.get(obj);
    }

    public HttpServletRequest request() {
        return request;
    }

    public void setThreadLocal(String key, Object value) {
        threadLocals.get().put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getThreadLocal(String key) {
        return (T) threadLocals.get().get(key);
    }

    /**
     * 创建线程安全的上下文副本
     */
    public ChainContext copy() {
        ChainContext copy = new ChainContext(this.request, this.response);

        // 深拷贝属性（确保值对象不可变）
        this.attributes.forEach((k, v) -> {
            if (v != null) {
                copy.attributes.put(k, deepCopySerializable(v));
            }
        });

        // 原子状态复制
        copy.terminated.set(this.terminated.get());

        // 元数据浅拷贝（假设元数据对象是线程安全的）
        copy.metadata.putAll(this.metadata);

        threadLocals.get().forEach(copy::setThreadLocal);
        return copy;
    }

    /**
     * 合并异步上下文变更到原始上下文
     * @param source 异步处理后的上下文副本
     */
    public void mergeFrom(ChainContext source) {
        // 原子状态合并（优先采用源状态）
        if (source.terminated.get()) {
            this.terminated.compareAndSet(false, true);
        }

        // 属性合并策略：源上下文属性优先
        source.attributes.forEach((key, value) -> {
            if (value != null) {
                this.attributes.merge(key, value, (oldVal, newVal) -> newVal);
            }
        });

        // 元数据合并策略：仅新增元数据
        source.metadata.forEach(this.metadata::putIfAbsent);

        // 同步响应状态码
        if (source.response.isCommitted()) {
            try {
                this.response.setStatus(source.response.getStatus());
                source.response.getHeaderNames().forEach(header ->
                        this.response.setHeader(header,
                                source.response.getHeader(header)));
            } catch (IllegalStateException ignored) {
                // 响应已提交时的容错处理
            }
        }
    }

    /**
     * 安全的序列化深拷贝方法
     */
    @SuppressWarnings("unchecked")
    private static <T extends Serializable> T deepCopySerializable(T object) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(object);
            oos.flush();

            try (ObjectInputStream ois = new ObjectInputStream(
                    new ByteArrayInputStream(bos.toByteArray()))) {
                return (T) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new ChainException("Context copy failed", e);
        }
    }

    public void setCurrentHandler(EnhancedHandler  handler) {
        this.currentHandler = handler;
    }
}