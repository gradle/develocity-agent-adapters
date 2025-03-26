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

import javax.annotation.Nullable;

public interface BuildCacheConfigurationAdapter {

    LocalBuildCacheAdapter getLocal();

    @Nullable
    RemoteBuildCacheAdapter getRemote();

    /**
     *  @see org.gradle.caching.local.DirectoryBuildCache
     */
    interface LocalBuildCacheAdapter {
        boolean isEnabled();

        void setEnabled(boolean enabled);

        boolean isPush();

        void setPush(boolean storeEnabled);

        String getDirectory();

        void setDirectory(String directory);

        int getRemoveUnusedEntriesAfterDays();

        void setRemoveUnusedEntriesAfterDays(int days);
    }

    /**
     *  @see org.gradle.caching.http.HttpBuildCache
     *  @see com.gradle.develocity.agent.gradle.buildcache.DevelocityBuildCache
     *  @see com.gradle.enterprise.gradleplugin.GradleEnterpriseBuildCache
     */
    interface RemoteBuildCacheAdapter {
        boolean isEnabled();

        void setEnabled(boolean enabled);

        boolean isPush();

        void setPush(boolean storeEnabled);

        @Nullable
        String getUrl();

        void setUrl(@Nullable String url);

        @Nullable
        String getServer();

        void setServer(@Nullable String server);

        @Nullable
        String getPath();

        void setPath(@Nullable String path);

        boolean getAllowUntrustedServer();

        void setAllowUntrustedServer(boolean allowUntrusted);

        boolean getAllowInsecureProtocol();

        void setAllowInsecureProtocol(boolean allowInsecureProtocol);

        boolean getUseExpectContinue();

        void setUseExpectContinue(boolean useExpectContinue);

        @Nullable
        Object getUsernameAndPassword();

        void usernameAndPassword(String username, String password);
    }

}
