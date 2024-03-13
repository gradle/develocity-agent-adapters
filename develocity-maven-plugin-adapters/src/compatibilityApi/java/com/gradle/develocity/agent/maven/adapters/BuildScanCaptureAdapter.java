package com.gradle.develocity.agent.maven.adapters;

public interface BuildScanCaptureAdapter {

    void setGoalInputFiles(boolean capture);

    boolean isGoalInputFiles();

    void setBuildLogging(boolean capture);

    boolean isBuildLogging();

    void setTestLogging(boolean capture);

    boolean isTestLogging();

}
