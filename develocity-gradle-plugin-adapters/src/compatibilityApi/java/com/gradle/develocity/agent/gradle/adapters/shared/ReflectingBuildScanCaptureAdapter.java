package com.gradle.develocity.agent.gradle.adapters.shared;

import com.gradle.develocity.agent.gradle.adapters.BuildScanCaptureAdapter;
import com.gradle.develocity.agent.gradle.adapters.internal.ReflectionProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;

public class ReflectingBuildScanCaptureAdapter implements BuildScanCaptureAdapter {
    private final ReflectionProperty<Boolean> fileFingerprints;
    private final ReflectionProperty<Boolean> buildLogging;
    private final ReflectionProperty<Boolean> testLogging;

    public ReflectingBuildScanCaptureAdapter(ReflectionProperty<Boolean> fileFingerprints, ReflectionProperty<Boolean> buildLogging, ReflectionProperty<Boolean> testLogging) {
        this.fileFingerprints = fileFingerprints;
        this.buildLogging = buildLogging;
        this.testLogging = testLogging;
    }

    @Override
    public void setFileFingerprints(boolean capture) {
        fileFingerprints.set(capture);
    }

    @Override
    public boolean isFileFingerprints() {
        return fileFingerprints.get();
    }

    @Override
    public void setBuildLogging(boolean capture) {
        buildLogging.set(capture);
    }

    @Override
    public boolean isBuildLogging() {
        return buildLogging.get();
    }

    @Override
    public void setTestLogging(boolean capture) {
        testLogging.set(capture);
    }

    @Override
    public boolean isTestLogging() {
        return testLogging.get();
    }
}
