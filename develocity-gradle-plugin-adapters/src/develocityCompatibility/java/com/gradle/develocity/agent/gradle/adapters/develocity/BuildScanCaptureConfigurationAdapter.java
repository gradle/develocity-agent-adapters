package com.gradle.develocity.agent.gradle.adapters.develocity;

import com.gradle.develocity.agent.gradle.adapters.BuildScanCaptureAdapter;
import com.gradle.develocity.agent.gradle.adapters.internal.ReflectionProperty;
import com.gradle.develocity.agent.gradle.adapters.internal.ReflectionUtils;
import com.gradle.develocity.agent.gradle.adapters.shared.ReflectingBuildScanCaptureAdapter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;


public class BuildScanCaptureConfigurationAdapter {
    public static BuildScanCaptureAdapter forBuildScanExtension(Object buildScan) {
        Object capture = ReflectionUtils.invokeMethod(buildScan, "getCapture");
        return forCaptureConfiguration(capture);
    }

    @VisibleForTesting
    static @NotNull ReflectingBuildScanCaptureAdapter forCaptureConfiguration(Object capture) {
        return new ReflectingBuildScanCaptureAdapter(
            ReflectionProperty.forProperty(capture, "getFileFingerprints"),
            ReflectionProperty.forProperty(capture, "getBuildLogging"),
            ReflectionProperty.forProperty(capture, "getTestLogging")
        );
    }
}
