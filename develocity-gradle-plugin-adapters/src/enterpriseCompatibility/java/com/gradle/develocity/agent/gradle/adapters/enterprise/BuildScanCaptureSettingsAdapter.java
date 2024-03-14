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

package com.gradle.develocity.agent.gradle.adapters.enterprise;

import com.gradle.develocity.agent.gradle.adapters.BuildScanCaptureAdapter;
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
