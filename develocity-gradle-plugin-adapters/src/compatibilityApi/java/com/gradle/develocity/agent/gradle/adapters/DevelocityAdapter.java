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

package com.gradle.develocity.agent.gradle.adapters;

import org.gradle.api.Action;
import org.gradle.caching.configuration.AbstractBuildCache;

import javax.annotation.Nullable;

public interface DevelocityAdapter {

    BuildScanAdapter getBuildScan();

    void buildScan(Action<? super BuildScanAdapter> action);

    void setServer(@Nullable String server);

    @Nullable
    String getServer();

    void setProjectId(@Nullable String projectId);

    @Nullable
    String getProjectId();

    void setAllowUntrustedServer(boolean allow);

    boolean getAllowUntrustedServer();

    void setAccessKey(@Nullable String accessKey);

    @Nullable
    String getAccessKey();

    @Nullable
    Class<? extends AbstractBuildCache> getBuildCache();

}
