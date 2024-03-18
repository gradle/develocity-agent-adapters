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

package com.gradle.develocity.agent.maven.adapters;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.gradle.develocity.agent.maven.adapters.ReflectionUtils.invokeMethod;

public final class Property<T> {

    private final Consumer<T> setter;
    private final Supplier<T> getter;

    private Property(Consumer<T> setter, Supplier<T> getter) {
        this.setter = setter;
        this.getter = getter;
    }

    public static <T> Property<T> create(Consumer<T> setter, Supplier<T> getter) {
        return new Property<>(setter, getter);
    }

    public static <T> Property<T> optional(Object obj, String setterName, String getterName) {
        return new Property<>(
            value -> setIfSupported(obj, setterName, value),
            () -> getIfSupported(obj, getterName)
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
    private static <T> T getIfSupported(Object obj, String method) {
        return (T) invokeMethod(obj, method);
    }

}
