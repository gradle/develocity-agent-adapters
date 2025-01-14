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
import com.gradle.scan.plugin.BuildScanExtension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BuildScanCapturePropertyAdapter implements BuildScanCaptureAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(BuildScanCapturePropertyAdapter.class);

    private final BuildScanExtension buildScan;

    BuildScanCapturePropertyAdapter(BuildScanExtension buildScan) {
        this.buildScan = buildScan;
    }

    @Override
    public void setFileFingerprints(boolean capture) {
        this.buildScan.setCaptureTaskInputFiles(capture);
    }

    @Override
    public boolean isFileFingerprints() {
        return this.buildScan.isCaptureTaskInputFiles();
    }

    @Override
    public void setBuildLogging(boolean capture) {
        warnAboutUnsupportedOperation("capture.buildLogging");
    }

    @Override
    public boolean isBuildLogging() {
        warnAboutUnsupportedOperation("capture.buildLogging");
        return true; // Build logging will always be captured
    }

    @Override
    public void setTestLogging(boolean capture) {
        warnAboutUnsupportedOperation("capture.testLogging");
    }

    @Override
    public boolean isTestLogging() {
        warnAboutUnsupportedOperation("capture.testLogging");
        return true; // Test logging will always be captured
    }

    private static void warnAboutUnsupportedOperation(String op) {
        LOG.warn("Build Scan Extension does not support '" + op + "' operation");
    }
}
