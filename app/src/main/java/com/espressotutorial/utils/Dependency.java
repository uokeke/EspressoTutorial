package com.espressotutorial.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Uche on 2016-04-04.
 */
public class Dependency {

    private static Dependency instance = null;

    public static synchronized Dependency getInstance() {
        if (instance == null)
            instance = new Dependency();
        return instance;
    }

    private final Map<Class<?>, Object> dependencies;

    private Dependency() {
        dependencies = new HashMap<>();
    }

    public <T extends Object> T get(Class<T> type) {
        for (Class<?> dependencyType : dependencies.keySet()) {
            if (type.isAssignableFrom(dependencyType))
                return (T) dependencies.get(dependencyType);
        }
        throw new RuntimeException("Cannot find a registered dependency for " + type.getName());
    }

    public <T extends Object> void register(Class<T> type, T object) {
        dependencies.put(type, object);
    }

    public void clear() {
        dependencies.clear();
    }

}
