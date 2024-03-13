package com.gradle.develocity.agent.gradle.adapters;

public interface BuildScanCaptureAdapter {

    void setFileFingerprints(boolean capture);

    boolean isFileFingerprints();

    void setBuildLogging(boolean capture);

    boolean isBuildLogging();

    void setTestLogging(boolean capture);

    boolean isTestLogging();

}
