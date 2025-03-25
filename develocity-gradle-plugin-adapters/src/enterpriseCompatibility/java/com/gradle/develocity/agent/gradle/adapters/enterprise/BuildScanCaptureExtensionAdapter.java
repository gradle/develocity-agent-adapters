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
