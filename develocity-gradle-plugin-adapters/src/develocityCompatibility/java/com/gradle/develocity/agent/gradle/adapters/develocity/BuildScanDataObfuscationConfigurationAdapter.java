package com.gradle.develocity.agent.gradle.adapters.develocity;

import com.gradle.develocity.agent.gradle.adapters.internal.ReflectionUtils;
import com.gradle.develocity.agent.gradle.adapters.shared.ReflectingBuildScanObfuscationAdapter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;

public class BuildScanDataObfuscationConfigurationAdapter {
    public static ReflectingBuildScanObfuscationAdapter forBuildScanExtension(Object buildScan) {
        Object obfuscation = ReflectionUtils.invokeMethod(buildScan, "getObfuscation");
        return forObfuscationConfiguration(obfuscation);
    }

    @VisibleForTesting
    public static @NotNull ReflectingBuildScanObfuscationAdapter forObfuscationConfiguration(Object obfuscation) {
        return new ReflectingBuildScanObfuscationAdapter(obfuscation);
    }
}
