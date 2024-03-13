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

import com.gradle.develocity.agent.gradle.adapters.BuildScanAdapter;
import com.gradle.develocity.agent.gradle.adapters.DevelocityAdapter;
import com.gradle.develocity.agent.gradle.DevelocityConfiguration;
import org.gradle.api.Action;
import org.gradle.caching.configuration.AbstractBuildCache;
import org.jetbrains.annotations.Nullable;

public class DevelocityConfigurationAdapter implements DevelocityAdapter {

    private final DevelocityConfiguration configuration;
    private final BuildScanConfigurationAdapter buildScan;

    public DevelocityConfigurationAdapter(DevelocityConfiguration configuration) {
        this.configuration = configuration;
        this.buildScan = new BuildScanConfigurationAdapter(configuration.getBuildScan());
    }

    @Override
    public BuildScanAdapter getBuildScan() {
        return buildScan;
    }

    @Override
    public void buildScan(Action<? super BuildScanAdapter> action) {
        action.execute(buildScan);
    }

    @Override
    public void setServer(@Nullable String server) {
        configuration.getServer().set(server);
    }

    @Nullable
    @Override
    public String getServer() {
        return configuration.getServer().getOrNull();
    }

    @Override
    public void setProjectId(@Nullable String projectId) {
        configuration.getProjectId().set(projectId);
    }

    @Nullable
    @Override
    public String getProjectId() {
        return configuration.getProjectId().getOrNull();
    }

    @Override
    public void setAllowUntrustedServer(boolean allow) {
        configuration.getAllowUntrustedServer().set(allow);
    }

    @Override
    public boolean getAllowUntrustedServer() {
        return configuration.getAllowUntrustedServer().get();
    }

    @Override
    public void setAccessKey(@Nullable String accessKey) {
        configuration.getAccessKey().set(accessKey);
    }

    @Nullable
    @Override
    public String getAccessKey() {
        return configuration.getAccessKey().getOrNull();
    }

    @Nullable
    @Override
    public Class<? extends AbstractBuildCache> getBuildCache() {
        return configuration.getBuildCache();
    }
}
