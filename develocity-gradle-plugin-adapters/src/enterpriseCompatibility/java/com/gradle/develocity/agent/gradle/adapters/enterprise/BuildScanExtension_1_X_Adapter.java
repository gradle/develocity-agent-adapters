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

import com.gradle.develocity.agent.gradle.adapters.BuildScanAdapter;
import com.gradle.develocity.agent.gradle.adapters.BuildScanCaptureAdapter;
import com.gradle.develocity.agent.gradle.adapters.BuildScanObfuscationAdapter;
import com.gradle.develocity.agent.gradle.adapters.DevelocityAdapter;
import com.gradle.develocity.agent.gradle.adapters.PublishedBuildScanAdapter;
import com.gradle.develocity.agent.gradle.adapters.internal.ProxyFactory;
import com.gradle.develocity.agent.gradle.adapters.internal.ReflectionProperty;
import com.gradle.develocity.agent.gradle.adapters.shared.BasicReflectingBuildScanAdapter;
import com.gradle.scan.plugin.BuildScanExtension;
import org.gradle.api.Action;
import org.gradle.caching.configuration.AbstractBuildCache;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

import static com.gradle.develocity.agent.gradle.adapters.internal.AdapterTypeUtils.checkIsBuildScanExtension;
import static com.gradle.develocity.agent.gradle.adapters.internal.ReflectionUtils.invokeMethod;

/**
 * Build Scan plugin 1.x registers the build scan extension as the root extension.
 * This adapter abstracts this detail away and allows interacting with the extension both as the root "develocity" extension and as the "buildScan" extension
 */
public class BuildScanExtension_1_X_Adapter extends BasicReflectingBuildScanAdapter implements DevelocityAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(BuildScanExtension_1_X_Adapter.class);

    private final BuildScanExtension extension;

    public BuildScanExtension_1_X_Adapter(Object extension) {
        super(proxyBuildScanExtension(extension));
        this.extension = (BuildScanExtension) buildScanExtension;
    }

    private static BuildScanExtension proxyBuildScanExtension(Object extension) {
        checkIsBuildScanExtension(extension);
        return ProxyFactory.createProxy(extension, BuildScanExtension.class);
    }

    @Override
    protected ReflectionProperty<String> getTermsOfUseUrlProperty() {
        return ReflectionProperty.create(extension, "getTermsOfServiceUrl", "setTermsOfServiceUrl");
    }

    @Override
    protected ReflectionProperty<String> getTermsOfUseAgreeProperty() {
        return ReflectionProperty.create(extension, "getTermsOfServiceAgree", "setTermsOfServiceAgree");
    }

    @Override
    protected ReflectionProperty<Boolean> getUploadInBackgroundProperty() {
        return ReflectionProperty.unsupported("isUploadInBackground", "setUploadInBackground", false);
    }

    @Override
    public void setUploadInBackground(boolean uploadInBackground) {
        warnAboutUnsupportedOperation("setUploadInBackground(boolean)");
    }

    @Override
    public boolean isUploadInBackground() {
        warnAboutUnsupportedOperation("isUploadInBackground()");
        return false;
    }

    @Override
    public BuildScanObfuscationAdapter getObfuscation() {
        warnAboutUnsupportedOperation("getObfuscation()");
        return null;
    }

    @Override
    public void obfuscation(Action<? super BuildScanObfuscationAdapter> action) {
        warnAboutUnsupportedOperation("obfuscation(Action)");
    }

    @Override
    public BuildScanCaptureAdapter getCapture() {
        warnAboutUnsupportedOperation("getCapture()");
        return null;
    }

    @Override
    public void capture(Action<? super BuildScanCaptureAdapter> action) {
        warnAboutUnsupportedOperation("capture(Action)");
    }

    @Override
    public BuildScanAdapter getBuildScan() {
        return this;
    }

    @Override
    public void buildScan(Action<? super BuildScanAdapter> action) {
        action.execute(this);
    }

    @Override
    public void setServer(@Nullable String server) {
        invokeMethod(buildScanExtension, "setServer", server);
    }

    @Nullable
    @Override
    public String getServer() {
        warnAboutUnsupportedOperation("getServer()");
        return null;
    }

    @Override
    public void setProjectId(@Nullable String projectId) {
        warnAboutUnsupportedOperation("setProjectId(String)");
    }

    @Nullable
    @Override
    public String getProjectId() {
        warnAboutUnsupportedOperation("getProjectId()");
        return null;
    }

    @Override
    public void setAllowUntrustedServer(boolean allow) {
        invokeMethod(buildScanExtension, "setAllowUntrustedServer", allow);
    }

    @Override
    public boolean getAllowUntrustedServer() {
        warnAboutUnsupportedOperation("getAllowUntrustedServer()");
        return false;
    }

    @Override
    public void setAccessKey(@Nullable String accessKey) {
        warnAboutUnsupportedOperation("setAccessKey()");
    }

    @Nullable
    @Override
    public String getAccessKey() {
        warnAboutUnsupportedOperation("getAccessKey()");
        return null;
    }

    @Override
    public Class<? extends AbstractBuildCache> getBuildCache() {
        warnAboutUnsupportedOperation("getBuildCache()");
        return null;
    }

    private static void warnAboutUnsupportedOperation(String op) {
        LOG.warn("Build Scan plugin version 1.x does not support '" + op + "' operation");
    }
}
