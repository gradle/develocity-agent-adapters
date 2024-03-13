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

package com.gradle.develocity.agent.gradle.adapters.develocity;

import com.gradle.develocity.agent.gradle.adapters.BuildScanCaptureAdapter;
import com.gradle.develocity.agent.gradle.scan.BuildScanCaptureConfiguration;

class BuildScanCaptureConfigurationAdapter implements BuildScanCaptureAdapter {

    private final BuildScanCaptureConfiguration capture;

    BuildScanCaptureConfigurationAdapter(BuildScanCaptureConfiguration capture) {
        this.capture = capture;
    }

    @Override
    public void setFileFingerprints(boolean capture) {
        this.capture.getFileFingerprints().set(capture);
    }

    @Override
    public boolean isFileFingerprints() {
        return capture.getFileFingerprints().get();
    }

    @Override
    public void setBuildLogging(boolean capture) {
        this.capture.getBuildLogging().set(capture);
    }

    @Override
    public boolean isBuildLogging() {
        return capture.getBuildLogging().get();
    }

    @Override
    public void setTestLogging(boolean capture) {
        this.capture.getTestLogging().set(capture);
    }

    @Override
    public boolean isTestLogging() {
        return capture.getTestLogging().get();
    }
}
