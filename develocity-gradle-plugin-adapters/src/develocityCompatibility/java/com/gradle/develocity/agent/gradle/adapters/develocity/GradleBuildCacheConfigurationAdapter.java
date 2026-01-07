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

import static com.gradle.develocity.agent.gradle.adapters.internal.ReflectionUtils.invokeMethod;

import com.gradle.develocity.agent.gradle.adapters.BuildCacheConfigurationAdapter;
import com.gradle.develocity.agent.gradle.adapters.internal.ReflectionProperty;

import org.gradle.caching.configuration.BuildCache;
import org.gradle.caching.configuration.BuildCacheConfiguration;
import org.gradle.caching.http.HttpBuildCache;
import org.gradle.caching.local.DirectoryBuildCache;
import org.jetbrains.annotations.Nullable;

import java.net.URI;

/**
 * Provides a common adapter layer for any `BuildCacheConfiguration` instance supplied by Gradle.
 * Several different remote build cache implementations are adapted:
 * - org.gradle.caching.http.HttpBuildCache
 * - com.gradle.develocity.agent.gradle.buildcache.DevelocityBuildCache
 * - com.gradle.enterprise.gradleplugin.GradleEnterpriseBuildCache
 */
public class GradleBuildCacheConfigurationAdapter implements BuildCacheConfigurationAdapter {

    private final BuildCacheConfiguration buildCache;

    public GradleBuildCacheConfigurationAdapter(BuildCacheConfiguration buildCache) {
        this.buildCache = buildCache;
    }

    @Override
    public LocalBuildCacheAdapter getLocal() {
        return new ReflectingLocalBuildCache(buildCache.getLocal());
    }

    @Override
    public @Nullable RemoteBuildCacheAdapter getRemote() {
        BuildCache remoteConfig = buildCache.getRemote();
        if (remoteConfig == null) {
            return null;
        } else {
            return new ReflectingRemoteBuildCache(remoteConfig);
        }
    }

    private static class ReflectingLocalBuildCache implements LocalBuildCacheAdapter {
        private final DirectoryBuildCache localBuildCache;
        private final ReflectionProperty<Integer> removeUnusedEntriesAfterDays;

        private ReflectingLocalBuildCache(DirectoryBuildCache localBuildCache) {
            this.localBuildCache = localBuildCache;
            this.removeUnusedEntriesAfterDays = ReflectionProperty.forGetterAndSetter(localBuildCache, "getRemoveUnusedEntriesAfterDays", "setRemoveUnusedEntriesAfterDays");
        }

        @Override
        public boolean isEnabled() {
            return localBuildCache.isEnabled();
        }

        @Override
        public void setEnabled(boolean enabled) {
            localBuildCache.setEnabled(enabled);
        }

        @Override
        public boolean isPush() {
            return localBuildCache.isPush();
        }

        @Override
        public void setPush(boolean push) {
            localBuildCache.setPush(push);
        }

        @Override
        public String getDirectory() {
            return String.valueOf(localBuildCache.getDirectory());
        }

        @Override
        public void setDirectory(String directory) {
            localBuildCache.setDirectory(directory);
        }

        @Override
        public int getRemoveUnusedEntriesAfterDays() {
            return removeUnusedEntriesAfterDays.get();
        }

        @Override
        public void setRemoveUnusedEntriesAfterDays(int days) {
            removeUnusedEntriesAfterDays.set(days);
        }
    }

    private static class ReflectingRemoteBuildCache implements RemoteBuildCacheAdapter {
        private final BuildCache remoteBuildCache;
        private final ReflectionProperty<URI> url;
        private final ReflectionProperty<String> server;
        private final ReflectionProperty<String> path;
        private final ReflectionProperty<Boolean> allowUntrustedServer;
        private final ReflectionProperty<Boolean> allowInsecureProtocol;
        private final ReflectionProperty<Boolean> useExpectContinue;

        private ReflectingRemoteBuildCache(BuildCache remoteBuildCache) {
            // Don't need to use Reflection for core Gradle type `BuildCache`.
            this.remoteBuildCache = remoteBuildCache;

            // Need to use reflection to access any methods declared on subtypes.
            this.url = ReflectionProperty.forGetterAndSetter(remoteBuildCache, "getUrl", "setUrl");
            this.server = ReflectionProperty.forGetterAndSetter(remoteBuildCache, "getServer", "setServer");
            this.path = ReflectionProperty.forGetterAndSetter(remoteBuildCache, "getPath", "setPath");
            if (remoteBuildCache instanceof HttpBuildCache) {
                this.allowUntrustedServer = ReflectionProperty.forGetterAndSetter(remoteBuildCache, "isAllowUntrustedServer", "setAllowUntrustedServer", false);
                this.allowInsecureProtocol = ReflectionProperty.forGetterAndSetter(remoteBuildCache, "isAllowInsecureProtocol", "setAllowInsecureProtocol", false);
                this.useExpectContinue = ReflectionProperty.forGetterAndSetter(remoteBuildCache, "isUseExpectContinue", "setUseExpectContinue", false);
            } else {
                this.allowUntrustedServer = ReflectionProperty.forGetterAndSetter(remoteBuildCache, "getAllowUntrustedServer", "setAllowUntrustedServer", false);
                this.allowInsecureProtocol = ReflectionProperty.forGetterAndSetter(remoteBuildCache, "getAllowInsecureProtocol", "setAllowInsecureProtocol", false);
                this.useExpectContinue = ReflectionProperty.forGetterAndSetter(remoteBuildCache, "getUseExpectContinue", "setUseExpectContinue", false);
            }
        }

        @Override
        public boolean isEnabled() {
            return remoteBuildCache.isEnabled();
        }

        @Override
        public void setEnabled(boolean enabled) {
            remoteBuildCache.setEnabled(enabled);
        }

        @Override
        public boolean isPush() {
            return remoteBuildCache.isPush();
        }

        @Override
        public void setPush(boolean push) {
            remoteBuildCache.setPush(push);
        }

        @Override
        public @Nullable String getUrl() {
            URI value = url.get();
            return value == null ? null : value.toASCIIString();
        }

        @Override
        public void setUrl(@Nullable String value) {
            URI uriValue = value == null ? null : URI.create(value);
            url.set(uriValue);
        }

        @Override
        public @Nullable String getServer() {
            return server.get();
        }

        @Override
        public void setServer(@Nullable String value) {
            server.set(value);
        }

        @Override
        public @Nullable String getPath() {
            return path.get();
        }

        @Override
        public void setPath(@Nullable String value) {
            path.set(value);
        }

        @Override
        public boolean getAllowUntrustedServer() {
            return allowUntrustedServer.get();
        }

        @Override
        public void setAllowUntrustedServer(boolean value) {
            allowUntrustedServer.set(value);
        }

        @Override
        public boolean getAllowInsecureProtocol() {
            return allowInsecureProtocol.get();
        }

        @Override
        public void setAllowInsecureProtocol(boolean value) {
            allowInsecureProtocol.set(value);
        }

        @Override
        public boolean getUseExpectContinue() {
            return useExpectContinue.get();
        }

        @Override
        public void setUseExpectContinue(boolean value) {
            useExpectContinue.set(value);
        }

        @Override
        public @Nullable Object getUsernameAndPassword() {
            return invokeMethod(remoteBuildCache, "getUsernameAndPassword");
        }

        @Override
        public void usernameAndPassword(String username, String password) {
            invokeMethod(remoteBuildCache, "usernameAndPassword", username, password);
        }
    }
}
