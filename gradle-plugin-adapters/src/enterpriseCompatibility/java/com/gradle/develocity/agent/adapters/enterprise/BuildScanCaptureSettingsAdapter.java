package com.gradle.develocity.agent.adapters.enterprise;

import com.gradle.develocity.agent.adapters.BuildScanCaptureAdapter;
import com.gradle.scan.plugin.BuildScanCaptureSettings;

class BuildScanCaptureSettingsAdapter implements BuildScanCaptureAdapter {

    private final BuildScanCaptureSettings capture;

    BuildScanCaptureSettingsAdapter(BuildScanCaptureSettings capture) {
        this.capture = capture;
    }

    @Override
    public void setFileFingerprints(boolean capture) {
        this.capture.setTaskInputFiles(capture);
    }

    @Override
    public boolean isFileFingerprints() {
        return capture.isTaskInputFiles();
    }

    @Override
    public void setBuildLogging(boolean capture) {
        this.capture.setBuildLogging(capture);
    }

    @Override
    public boolean isBuildLogging() {
        return capture.isBuildLogging();
    }

    @Override
    public void setTestLogging(boolean capture) {
        this.capture.setTestLogging(capture);
    }

    @Override
    public boolean isTestLogging() {
        return capture.isTestLogging();
    }
}
