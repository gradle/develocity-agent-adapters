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

import java.util.Arrays;

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
            if (implementsInterface(clazz, interfaceName)) {
                return true;
            }

            clazz = clazz.getSuperclass();
        }

        return false;
    }

    private static boolean implementsInterface(Class<?> clazz, String interfaceName) {
        if (interfaceName.equals(clazz.getName())) {
            return true;
        }

        return Arrays.stream(clazz.getInterfaces()).anyMatch(it -> implementsInterface(it, interfaceName));
    }
}
