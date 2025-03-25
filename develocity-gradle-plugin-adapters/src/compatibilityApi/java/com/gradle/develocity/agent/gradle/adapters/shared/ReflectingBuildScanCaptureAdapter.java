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
