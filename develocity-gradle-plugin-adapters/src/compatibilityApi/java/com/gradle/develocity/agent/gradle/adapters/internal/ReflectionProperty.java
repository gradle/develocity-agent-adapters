/*
 *
 *  * Copyright 2024-2024 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package com.gradle.develocity.agent.gradle.adapters.internal;

import static com.gradle.develocity.agent.gradle.adapters.internal.ReflectionUtils.*;

import org.gradle.api.provider.Property;

import java.util.function.Consumer;
import java.util.function.Supplier;

// Retain anonymous classes to support config-cache in Gradle 7.x
@SuppressWarnings("Convert2Lambda")
public class ReflectionProperty<T> {
    private final Supplier<T> getter;
    private final Consumer<T> setter;

    private ReflectionProperty(Supplier<T> getter, Consumer<T> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    public static <T> ReflectionProperty<T> unsupported(String getterName, String setterName, T defaultValue) {
        return new ReflectionProperty<>(
            new Supplier<T>() {
                @Override
                public T get() {
                    warnAboutUnsupportedMethod(getterName);
                    return defaultValue;
                }
            },
            new Consumer<T>() {
                @Override
                public void accept(T value) {
                    warnAboutUnsupportedMethod(setterName);
                }
            }
        );
    }

    public static <T> ReflectionProperty<T> forGetterAndSetter(Object obj, String getterName, String setterName) {
        return forGetterAndSetter(obj, getterName, setterName, null);
    }

    public static <T> ReflectionProperty<T> forGetterAndSetter(Object obj, String getterName, String setterName, T defaultValue) {
        return new ReflectionProperty<>(
            new Supplier<T>() {
                @Override
                public T get() {
                    return getIfSupported(obj, getterName, defaultValue);
                }
            },
            new Consumer<T>() {
                @Override
                public void accept(T value) {
                    setIfSupported(obj, setterName, value);
                }
            }
        );
    }

    public static <T> ReflectionProperty<T> forProperty(Object obj, String propertyName) {
        return forProperty(obj, propertyName, null);
    }

    public static <T> ReflectionProperty<T> forProperty(Object obj, String propertyName, T defaultValue) {
        return new ReflectionProperty<>(
            new Supplier<T>() {
                @Override
                public T get() {
                    Property<T> prop = (Property<T>) invokeMethod(obj, propertyName);
                    return prop.getOrElse(defaultValue);
                }
            },
            new Consumer<T>() {
                @Override
                public void accept(T value) {
                    Property<T> prop = (Property<T>) invokeMethod(obj, propertyName);
                    prop.set(value);
                }
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
