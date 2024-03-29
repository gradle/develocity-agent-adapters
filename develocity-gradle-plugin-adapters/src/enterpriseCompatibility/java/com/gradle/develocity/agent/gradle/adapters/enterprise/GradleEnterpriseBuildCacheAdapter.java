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

import com.gradle.develocity.agent.gradle.adapters.BuildCacheAdapter;
import com.gradle.enterprise.gradleplugin.GradleEnterpriseBuildCache;
import org.jetbrains.annotations.Nullable;

public class GradleEnterpriseBuildCacheAdapter implements BuildCacheAdapter {

    private final GradleEnterpriseBuildCache buildCache;

    public GradleEnterpriseBuildCacheAdapter(Object buildCache) {
        this.buildCache = (GradleEnterpriseBuildCache) buildCache;
    }

    @Nullable
    @Override
    public String getServer() {
        return buildCache.getServer();
    }

    @Override
    public void setServer(@Nullable String server) {
        buildCache.setServer(server);
    }

    @Nullable
    @Override
    public String getPath() {
        return buildCache.getPath();
    }

    @Override
    public void setPath(@Nullable String path) {
        buildCache.setPath(path);
    }

    @Nullable
    @Override
    public Boolean getAllowUntrustedServer() {
        return buildCache.getAllowUntrustedServer();
    }

    @Override
    public void setAllowUntrustedServer(boolean allowUntrustedServer) {
        buildCache.setAllowUntrustedServer(allowUntrustedServer);
    }

    @Override
    public boolean getAllowInsecureProtocol() {
        return buildCache.getAllowInsecureProtocol();
    }

    @Override
    public void setAllowInsecureProtocol(boolean allowInsecureProtocol) {
        buildCache.setAllowInsecureProtocol(allowInsecureProtocol);
    }

    @Override
    public void usernameAndPassword(String username, String password) {
        buildCache.usernameAndPassword(username, password);
    }

    @Nullable
    @Override
    public Object getUsernameAndPassword() {
        return buildCache.getUsernameAndPassword();
    }

    @Override
    public boolean getUseExpectContinue() {
        return buildCache.getUseExpectContinue();
    }

    @Override
    public void setUseExpectContinue(boolean useExpectContinue) {
        buildCache.setUseExpectContinue(useExpectContinue);
    }

    @Override
    public boolean isEnabled() {
        return buildCache.isEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {
        buildCache.setEnabled(enabled);
    }

    @Override
    public boolean isPush() {
        return buildCache.isPush();
    }

    @Override
    public void setPush(boolean enabled) {
        buildCache.setPush(enabled);
    }
}
