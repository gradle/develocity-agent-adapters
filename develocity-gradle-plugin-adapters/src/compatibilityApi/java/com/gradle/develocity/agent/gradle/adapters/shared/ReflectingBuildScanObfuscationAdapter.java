package com.gradle.develocity.agent.gradle.adapters.shared;

import static com.gradle.develocity.agent.gradle.adapters.internal.ReflectionUtils.invokeMethod;

import com.gradle.develocity.agent.gradle.adapters.BuildScanObfuscationAdapter;
import com.gradle.develocity.agent.gradle.adapters.internal.ReflectionUtils;

import java.net.InetAddress;
import java.util.List;
import java.util.function.Function;

/**
 * Transparently adapts obfuscation settings for Develocity and Gradle Enterprise types.
 * - com.gradle.develocity.agent.gradle.scan.BuildScanDataObfuscationConfiguration
 * - com.gradle.scan.plugin.BuildScanDataObfuscation
 */
public class ReflectingBuildScanObfuscationAdapter implements BuildScanObfuscationAdapter {
    private final Object obfuscation;

    public ReflectingBuildScanObfuscationAdapter(Object obfuscation) {
        this.obfuscation = obfuscation;
    }

    @Override
    public void username(Function<? super String, ? extends String> obfuscator) {
        invokeMethod(obfuscation, "username", obfuscator);
    }

    @Override
    public void hostname(Function<? super String, ? extends String> obfuscator) {
        invokeMethod(obfuscation, "hostname", obfuscator);
    }

    @Override
    public void ipAddresses(Function<? super List<InetAddress>, ? extends List<String>> obfuscator) {
        invokeMethod(obfuscation, "ipAddresses", obfuscator);
    }
}
