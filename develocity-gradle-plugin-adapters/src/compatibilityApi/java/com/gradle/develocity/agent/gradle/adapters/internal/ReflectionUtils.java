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

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtils.class);

    private ReflectionUtils() {}

    public static Object invokeMethod(Object obj, String method, Object... args) {
        try {
            Method maybeMethod = findMethod(obj, method, args);

            if (maybeMethod != null) {
                maybeMethod.setAccessible(true);
                return maybeMethod.invoke(obj, args);
            }

            warnAboutUnsupportedMethod(method);
            return null;
        } catch (ReflectiveOperationException e) {
            warnAboutUnsupportedMethod(method);
            return null;
        }
    }

    private static @Nullable Method findMethod(Object obj, String method, Object[] args) {
        // this is a simplified lookup which allows us to discover methods using both primitive and wrapper types
        List<Method> matchingMethods = Arrays.stream(obj.getClass().getMethods())
            .filter(it -> it.getName().equals(method)).collect(Collectors.toList());

        if (matchingMethods.isEmpty()) {
            return null;
        }
        if (matchingMethods.size() == 1) {
            return matchingMethods.get(0);
        }
        // Only when there is more than 1 candidate method do we inspect parameter types for compatibility
        for (Method matchingMethod : matchingMethods) {
            if (hasMatchingParameters(matchingMethod, args)) {
                return matchingMethod;
            }
        }
        return null;
    }

    private static boolean hasMatchingParameters(Method matchingMethod, Object[] args) {
        if (matchingMethod.getParameterCount() != args.length) {
            return false;
        }
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            Class<?> type = matchingMethod.getParameterTypes()[i];
            if (!type.isAssignableFrom(arg.getClass())) {
                return false;
            }
        }
        return true;
    }

    public static void withMethodSupported(Object obj, String method, Runnable action) {
        if (isMethodSupported(obj, method)) {
            action.run();
        } else {
            warnAboutUnsupportedMethod(method);
        }
    }

    public static boolean isMethodSupported(Object obj, String method) {
        return Arrays.stream(obj.getClass().getMethods())
            .anyMatch(it -> it.getName().equals(method));
    }

    public static void warnAboutUnsupportedMethod(String method) {
        LOGGER.warn("The '{}' method is not supported by this version of the Develocity extension", method);
    }
}
