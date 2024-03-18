package com.gradle.develocity.agent.gradle.adapters.internal;

import java.util.Arrays;
import java.util.stream.Stream;

public class AdapterTypeUtils {
    private AdapterTypeUtils() {
    }

    private static final String DEVELOCITY_CONFIGURATION = "com.gradle.develocity.agent.gradle.DevelocityConfiguration";
    private static final String GRADLE_ENTERPRISE_EXTENSION = "com.gradle.enterprise.gradleplugin.GradleEnterpriseExtension";
    private static final String BUILD_SCAN_EXTENSION = "com.gradle.scan.plugin.BuildScanExtension";

    public static void checkIsDevelocityConfiguration(Object gradleEnterpriseOrDevelocity) {
        throwIfNotImplementsInterface(gradleEnterpriseOrDevelocity, DEVELOCITY_CONFIGURATION, "Develocity configuration");
    }

    public static void checkIsGradleEnterpriseExtension(Object gradleEnterpriseOrDevelocity) {
        throwIfNotImplementsInterface(gradleEnterpriseOrDevelocity, GRADLE_ENTERPRISE_EXTENSION, "Gradle Enterprise extension");
    }

    public static void checkIsBuildScanExtension(Object gradleEnterpriseOrDevelocity) {
        throwIfNotImplementsInterface(gradleEnterpriseOrDevelocity, BUILD_SCAN_EXTENSION, "Build Scan extension");
    }

    private static void throwIfNotImplementsInterface(Object gradleEnterpriseOrDevelocity, String interfaceName, String expectedTypeDisplayName) {
        if (!implementsInterface(gradleEnterpriseOrDevelocity, interfaceName)) {
            throw new IllegalArgumentException("The supplied object of type '" + gradleEnterpriseOrDevelocity.getClass().getName() + "' is not a " + expectedTypeDisplayName + " object");
        }
    }

    private static boolean implementsInterface(Object object, String interfaceName) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            Class<?>[] interfaces = clazz.getInterfaces();
            boolean implementsInterface = Stream.concat(
                Arrays.stream(interfaces),
                Arrays.stream(interfaces).flatMap(it -> Arrays.stream(it.getInterfaces()))
            ).anyMatch(it -> interfaceName.equals(it.getName()));

            if (implementsInterface) {
                return true;
            }

            clazz = clazz.getSuperclass();
        }

        return false;
    }
}
