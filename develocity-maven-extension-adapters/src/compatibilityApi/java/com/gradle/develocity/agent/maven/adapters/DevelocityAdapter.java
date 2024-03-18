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

package com.gradle.develocity.agent.maven.adapters;

import java.net.URI;
import java.nio.file.Path;

/**
 * @see com.gradle.develocity.agent.maven.api.DevelocityApi
 * @see com.gradle.maven.extension.api.GradleEnterpriseApi
 */
public interface DevelocityAdapter {

    boolean isEnabled();

    void setEnabled(boolean enabled);

    void setProjectId(String projectId);

    String getProjectId();

    Path getStorageDirectory();

    void setStorageDirectory(Path path);

    default void setServer(String url) {
        setServer(url == null ? null : URI.create(url));
    }

    void setServer(URI url);

    String getServer();

    void setAllowUntrustedServer(boolean allow);

    boolean getAllowUntrustedServer();

    void setAccessKey(String accessKey);

    String getAccessKey();

    BuildScanApiAdapter getBuildScan();

    BuildCacheApiAdapter getBuildCache();

    boolean isDevelocityApi();

}
