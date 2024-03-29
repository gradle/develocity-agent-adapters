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
import com.gradle.develocity.agent.gradle.adapters.DevelocityAdapter;
import com.gradle.develocity.agent.gradle.adapters.internal.ProxyFactory;
import com.gradle.enterprise.gradleplugin.GradleEnterpriseExtension;
import org.gradle.api.Action;
import org.gradle.caching.configuration.AbstractBuildCache;
import org.jetbrains.annotations.Nullable;

import static com.gradle.develocity.agent.gradle.adapters.internal.AdapterTypeUtils.checkIsGradleEnterpriseExtension;

public class GradleEnterpriseExtensionAdapter implements DevelocityAdapter {

    private final GradleEnterpriseExtension extension;
    private final BuildScanExtensionAdapter buildScan;

    public GradleEnterpriseExtensionAdapter(Object extension) {
        checkIsGradleEnterpriseExtension(extension);
        this.extension = ProxyFactory.createProxy(extension, GradleEnterpriseExtension.class);
        this.buildScan = new BuildScanExtensionAdapter(this.extension.getBuildScan());
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
        extension.setServer(server);
    }

    @Nullable
    @Override
    public String getServer() {
        return extension.getServer();
    }

    @Override
    public void setProjectId(@Nullable String projectId) {
        extension.setProjectId(projectId);
    }

    @Nullable
    @Override
    public String getProjectId() {
        return extension.getProjectId();
    }

    @Override
    public void setAllowUntrustedServer(boolean allow) {
        extension.setAllowUntrustedServer(allow);
    }

    @Override
    public boolean getAllowUntrustedServer() {
        return extension.getAllowUntrustedServer();
    }

    @Override
    public void setAccessKey(@Nullable String accessKey) {
        extension.setAccessKey(accessKey);
    }

    @Nullable
    @Override
    public String getAccessKey() {
        return extension.getAccessKey();
    }

    @Nullable
    @Override
    public Class<? extends AbstractBuildCache> getBuildCache() {
        return extension.getBuildCache();
    }
}
