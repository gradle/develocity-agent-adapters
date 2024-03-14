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

import com.gradle.develocity.agent.maven.adapters.BuildCacheApiAdapter;
import com.gradle.develocity.agent.maven.adapters.BuildScanApiAdapter;
import com.gradle.develocity.agent.maven.adapters.DevelocityAdapter;
import com.gradle.develocity.agent.maven.adapters.Property;
import com.gradle.maven.extension.api.GradleEnterpriseApi;

import java.net.URI;
import java.nio.file.Path;

public class GradleEnterpriseApiAdapter implements DevelocityAdapter {

    private final GradleEnterpriseApi api;
    private final BuildScanApiAdapter buildScan;
    private final BuildCacheApiAdapter buildCache;
    private final Property<String> projectId;

    public GradleEnterpriseApiAdapter(GradleEnterpriseApi api) {
        this.api = api;
        this.buildScan = new GradleEnterpriseBuildScanApiAdapter(api.getBuildScan());
        this.buildCache = new GradleEnterpriseBuildCacheApiAdapter(api.getBuildCache());
        this.projectId = Property.optional(api, "setProjectId", "getProjectId");
    }

    @Override
    public boolean isEnabled() {
        return api.isEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {
        api.setEnabled(enabled);
    }

    @Override
    public void setProjectId(String projectId) {
        this.projectId.set(projectId);
    }

    @Override
    public String getProjectId() {
        return projectId.get();
    }

    @Override
    public Path getStorageDirectory() {
        return api.getStorageDirectory();
    }

    @Override
    public void setStorageDirectory(Path path) {
        api.setStorageDirectory(path);
    }

    @Override
    public void setServer(URI url) {
        api.setServer(url);
    }

    @Override
    public String getServer() {
        return api.getServer();
    }

    @Override
    public void setAllowUntrustedServer(boolean allow) {
        api.setAllowUntrustedServer(allow);
    }

    @Override
    public boolean getAllowUntrustedServer() {
        return api.getAllowUntrustedServer();
    }

    @Override
    public void setAccessKey(String accessKey) {
        api.setAccessKey(accessKey);
    }

    @Override
    public String getAccessKey() {
        return api.getAccessKey();
    }

    @Override
    public BuildScanApiAdapter getBuildScan() {
        return buildScan;
    }

    @Override
    public BuildCacheApiAdapter getBuildCache() {
        return buildCache;
    }

    @Override
    public boolean isDevelocityApi() {
        return false;
    }
}
