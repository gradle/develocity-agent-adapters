package com.gradle.develocity.agent.gradle.adapters.internal;

import static com.gradle.develocity.agent.gradle.adapters.internal.ReflectionUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ReflectionProperty<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionProperty.class);

    private final Supplier<T> getter;
    private final Consumer<T> setter;

    private ReflectionProperty(Supplier<T> getter, Consumer<T> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    public static <T> ReflectionProperty<T> create(Object obj, String getterName, String setterName) {
        return create(obj, getterName, setterName, null);
    }

    public static <T> ReflectionProperty<T> create(Object obj, String getterName, String setterName, T defaultValue) {
        return new ReflectionProperty<>(
            () -> getIfSupported(obj, getterName, defaultValue),
            value -> setIfSupported(obj, setterName, value)
        );
    }

    public void set(T value) {
        setter.accept(value);
    }

    public T get() {
        return getter.get();
    }

    private static <T> void setIfSupported(Object obj, String method, T value) {
        invokeMethod(obj, method, value);
    }

    @SuppressWarnings("unchecked")
    private static <T> T getIfSupported(Object obj, String method, T defaultValue) {
        T rawValue = (T) invokeMethod(obj, method);
        return rawValue == null ? defaultValue : rawValue;
    }

}
