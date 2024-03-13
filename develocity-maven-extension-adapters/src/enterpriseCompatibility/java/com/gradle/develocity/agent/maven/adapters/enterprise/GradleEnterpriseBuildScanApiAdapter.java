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

package com.gradle.develocity.agent.maven.adapters.enterprise;

import com.gradle.develocity.agent.maven.adapters.BuildResultAdapter;
import com.gradle.develocity.agent.maven.adapters.BuildScanApiAdapter;
import com.gradle.develocity.agent.maven.adapters.BuildScanCaptureAdapter;
import com.gradle.develocity.agent.maven.adapters.BuildScanDataObfuscationAdapter;
import com.gradle.develocity.agent.maven.adapters.Property;
import com.gradle.develocity.agent.maven.adapters.PublishedBuildScanAdapter;
import com.gradle.develocity.agent.maven.adapters.shared.DefaultBuildResultAdapter;
import com.gradle.develocity.agent.maven.adapters.shared.DefaultBuildScanCaptureAdapter;
import com.gradle.develocity.agent.maven.adapters.shared.DefaultBuildScanDataObfuscationAdapter;
import com.gradle.develocity.agent.maven.adapters.shared.DefaultPublishedBuildScanAdapter;
import com.gradle.maven.extension.api.scan.BuildScanApi;

import java.net.URI;
import java.util.function.Consumer;

class GradleEnterpriseBuildScanApiAdapter implements BuildScanApiAdapter {

    private final BuildScanApi buildScan;
    private final BuildScanDataObfuscationAdapter obfuscation;
    private final BuildScanCaptureAdapter capture;

    GradleEnterpriseBuildScanApiAdapter(BuildScanApi buildScan) {
        this.buildScan = buildScan;
        this.obfuscation = new DefaultBuildScanDataObfuscationAdapter(
            buildScan.getObfuscation()::username,
            buildScan.getObfuscation()::hostname,
            buildScan.getObfuscation()::ipAddresses
        );
        this.capture = new DefaultBuildScanCaptureAdapter(
            Property.create(buildScan.getCapture()::setGoalInputFiles, buildScan.getCapture()::isGoalInputFiles),
            Property.create(buildScan.getCapture()::setBuildLogging, buildScan.getCapture()::isBuildLogging),
            Property.create(buildScan.getCapture()::setTestLogging, buildScan.getCapture()::isTestLogging)
        );
    }

    @Override
    public void tag(String tag) {
        buildScan.tag(tag);
    }

    @Override
    public void value(String name, String value) {
        buildScan.value(name, value);
    }

    @Override
    public void link(String name, String url) {
        buildScan.link(name, url);
    }

    @Override
    public void background(Consumer<? super BuildScanApiAdapter> action) {
        buildScan.background(__ -> action.accept(this));
    }

    @Override
    public void buildFinished(Consumer<? super BuildResultAdapter> action) {
        buildScan.buildFinished(result -> action.accept(new DefaultBuildResultAdapter(result.getFailures())));
    }

    @Override
    public void buildScanPublished(Consumer<? super PublishedBuildScanAdapter> action) {
        buildScan.buildScanPublished(scan -> action.accept(new DefaultPublishedBuildScanAdapter(scan.getBuildScanId(), scan.getBuildScanUri())));
    }

    @Override
    public void setTermsOfUseUrl(String termsOfServiceUrl) {
        buildScan.setTermsOfServiceUrl(termsOfServiceUrl);
    }

    @Override
    public String getTermsOfUseUrl() {
        return buildScan.getTermsOfServiceUrl();
    }

    @Override
    public void setTermsOfUseAgree(String agree) {
        buildScan.setTermsOfServiceAgree(agree);
    }

    @Override
    public String getTermsOfUseAgree() {
        return buildScan.getTermsOfServiceAgree();
    }

    @Override
    public void setServer(URI url) {
        buildScan.setServer(url);
    }

    @Override
    public String getServer() {
        return buildScan.getServer();
    }

    @Override
    public void setAllowUntrustedServer(boolean allow) {
        buildScan.setAllowUntrustedServer(allow);
    }

    @Override
    public boolean getAllowUntrustedServer() {
        return buildScan.getAllowUntrustedServer();
    }

    @Override
    public void publishAlways() {
        buildScan.publishAlways();
    }

    @Override
    public void publishAlwaysIf(boolean condition) {
        buildScan.publishAlwaysIf(condition);
    }

    @Override
    public void publishOnFailure() {
        buildScan.publishOnFailure();
    }

    @Override
    public void publishOnFailureIf(boolean condition) {
        buildScan.publishOnFailureIf(condition);
    }

    @Override
    public void publishOnDemand() {
        buildScan.publishOnDemand();
    }

    @Override
    public void setUploadInBackground(boolean uploadInBackground) {
        buildScan.setUploadInBackground(uploadInBackground);
    }

    @Override
    public boolean isUploadInBackground() {
        return buildScan.isUploadInBackground();
    }

    @Override
    public void executeOnce(String identifier, Consumer<? super BuildScanApiAdapter> action) {
        buildScan.executeOnce(identifier, __ -> action.accept(this));
    }

    @Override
    public BuildScanDataObfuscationAdapter getObfuscation() {
        return obfuscation;
    }

    @Override
    public BuildScanCaptureAdapter getCapture() {
        return capture;
    }
}
