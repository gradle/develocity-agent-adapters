package com.gradle.develocity.agent.gradle.adapters.internal;

import static com.gradle.develocity.agent.gradle.adapters.internal.ReflectionUtils.*;

import org.gradle.api.provider.Property;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ReflectionProperty<T> {
    private final Supplier<T> getter;
    private final Consumer<T> setter;

    private ReflectionProperty(Supplier<T> getter, Consumer<T> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    public static <T> ReflectionProperty<T> unsupported(String getterName, String setterName, T defaultValue) {
        return new ReflectionProperty<>(
            () -> {
                warnAboutUnsupportedMethod(getterName);
                return defaultValue;
            },
            value -> warnAboutUnsupportedMethod(setterName)
        );
    }

    public static <T> ReflectionProperty<T> forGetterAndSetter(Object obj, String getterName, String setterName) {
        return forGetterAndSetter(obj, getterName, setterName, null);
    }

    public static <T> ReflectionProperty<T> forGetterAndSetter(Object obj, String getterName, String setterName, T defaultValue) {
        return new ReflectionProperty<>(
            () -> getIfSupported(obj, getterName, defaultValue),
            value -> setIfSupported(obj, setterName, value)
        );
    }

    public static <T> ReflectionProperty<T> forProperty(Object obj, String propertyName) {
        return new ReflectionProperty<>(
            () -> {
                Property<T> prop = (Property<T>) invokeMethod(obj, propertyName);
                return prop.get();
            },
            value -> {
                Property<T> prop = (Property<T>) invokeMethod(obj, propertyName);
                prop.set(value);
            }
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
