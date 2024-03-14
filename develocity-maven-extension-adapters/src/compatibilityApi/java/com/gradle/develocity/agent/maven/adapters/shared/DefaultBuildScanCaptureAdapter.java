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

package com.gradle.develocity.agent.maven.adapters.shared;

import com.gradle.develocity.agent.maven.adapters.BuildScanCaptureAdapter;
import com.gradle.develocity.agent.maven.adapters.Property;

public class DefaultBuildScanCaptureAdapter implements BuildScanCaptureAdapter {

    private final Property<Boolean> goalInputFiles;
    private final Property<Boolean> buildLogging;
    private final Property<Boolean> testLogging;

    public DefaultBuildScanCaptureAdapter(Property<Boolean> goalInputFiles, Property<Boolean> buildLogging, Property<Boolean> testLogging) {
        this.goalInputFiles = goalInputFiles;
        this.buildLogging = buildLogging;
        this.testLogging = testLogging;
    }

    @Override
    public void setGoalInputFiles(boolean capture) {
        goalInputFiles.set(capture);
    }

    @Override
    public boolean isGoalInputFiles() {
        return goalInputFiles.get();
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
