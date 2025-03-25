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

package com.gradle.develocity.agent.gradle.adapters.enterprise;

import com.gradle.develocity.agent.gradle.adapters.BuildScanCaptureAdapter;
import com.gradle.develocity.agent.gradle.adapters.internal.ReflectionProperty;
import com.gradle.develocity.agent.gradle.adapters.internal.ReflectionUtils;
import com.gradle.develocity.agent.gradle.adapters.shared.ReflectingBuildScanCaptureAdapter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;

public class BuildScanCaptureExtensionAdapter {

    public static BuildScanCaptureAdapter forBuildScanExtension(Object buildScan) {
        if (ReflectionUtils.isMethodSupported(buildScan, "getCapture")) {
            Object capture = ReflectionUtils.invokeMethod(buildScan, "getCapture");
            return forBuildScanCaptureSettings(capture);
        } else {
            return forBuildScanCaptureProperty(buildScan);
        }
    }

    @VisibleForTesting
    public static @NotNull BuildScanCaptureAdapter forBuildScanCaptureSettings(Object capture) {
        return new ReflectingBuildScanCaptureAdapter(
            ReflectionProperty.forGetterAndSetter(capture, "isTaskInputFiles", "setTaskInputFiles"),
            ReflectionProperty.forGetterAndSetter(capture, "isBuildLogging", "setBuildLogging", true),
            ReflectionProperty.forGetterAndSetter(capture, "isTestLogging", "setTestLogging", true)
        );
    }

    @VisibleForTesting
    public static @NotNull ReflectingBuildScanCaptureAdapter forBuildScanCaptureProperty(Object buildScan) {
        return new ReflectingBuildScanCaptureAdapter(
            ReflectionProperty.forGetterAndSetter(buildScan, "isCaptureTaskInputFiles", "setCaptureTaskInputFiles"),
            // TODO: We know these methods will never exist: could hard-code an 'unsupported' property
            ReflectionProperty.forGetterAndSetter(buildScan, "isBuildLogging", "setBuildLogging", true),
            ReflectionProperty.forGetterAndSetter(buildScan, "isTestLogging", "setTestLogging", true)
        );
    }

}
