package io.vincent.learning.stack.design.patterns.filterchain.v4;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MetadataManager {
    private final Map<Class<?>, Object> metadata = new ConcurrentHashMap<>();
    
    public <T> void register(Class<T> type, T instance) {
        metadata.put(type, type.cast(instance));
    }
    
    public <T> Optional<T> resolve(Class<T> type) {
        return Optional.ofNullable(type.cast(metadata.get(type)));
    }
}