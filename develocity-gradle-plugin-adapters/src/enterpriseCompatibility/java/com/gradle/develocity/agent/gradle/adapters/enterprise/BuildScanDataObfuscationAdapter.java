package com.gradle.develocity.agent.gradle.adapters.enterprise;

import com.gradle.develocity.agent.gradle.adapters.BuildScanObfuscationAdapter;
import com.gradle.develocity.agent.gradle.adapters.internal.ReflectionUtils;
import com.gradle.develocity.agent.gradle.adapters.shared.ReflectingBuildScanObfuscationAdapter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;

public class BuildScanDataObfuscationAdapter {
    public static BuildScanObfuscationAdapter forBuildScanExtension(Object buildScan) {
        Object obfuscation = ReflectionUtils.invokeMethod(buildScan, "getObfuscation");
        return forObfuscationExtension(obfuscation);
    }

    @VisibleForTesting
    public static @NotNull BuildScanObfuscationAdapter forObfuscationExtension(Object obfuscation) {
        return new ReflectingBuildScanObfuscationAdapter(obfuscation);
    }
}
