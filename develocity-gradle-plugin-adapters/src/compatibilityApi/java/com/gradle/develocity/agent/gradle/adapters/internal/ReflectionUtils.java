package com.gradle.develocity.agent.gradle.adapters.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class ReflectionUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtils.class);

    private ReflectionUtils() {}

    public static Object invokeMethod(Object obj, String method, Object... args) {
        try {
            // this is a simplified lookup which allows us to discover methods using both primitive and wrapper types
            Optional<Method> maybeMethod = Arrays.stream(obj.getClass().getMethods())
                .filter(it -> it.getName().equals(method))
                .findFirst();

            if (maybeMethod.isPresent()) {
                return maybeMethod.get().invoke(obj, args);
            }

            warnAboutUnsupportedMethod(method);
            return null;
        } catch (ReflectiveOperationException e) {
            warnAboutUnsupportedMethod(method);
            return null;
        }
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
