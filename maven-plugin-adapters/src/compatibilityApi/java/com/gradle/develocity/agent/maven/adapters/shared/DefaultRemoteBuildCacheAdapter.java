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

package com.gradle.develocity.agent.maven.adapters.shared;

import com.gradle.develocity.agent.maven.adapters.Property;
import com.gradle.develocity.agent.maven.adapters.RemoteBuildCacheAdapter;
import com.gradle.develocity.agent.maven.adapters.ServerAdapter;

public class DefaultRemoteBuildCacheAdapter implements RemoteBuildCacheAdapter {

    private final Property<Boolean> enabled;
    private final Property<Boolean> storeEnabled;
    private final ServerAdapter serverAdapter;

    public DefaultRemoteBuildCacheAdapter(
        Property<Boolean> enabled,
        Property<Boolean> storeEnabled,
        ServerAdapter serverAdapter
    ) {
        this.enabled = enabled;
        this.storeEnabled = storeEnabled;
        this.serverAdapter = serverAdapter;
    }

    @Override
    public boolean isEnabled() {
        return enabled.get();
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled.set(enabled);
    }

    @Override
    public boolean isStoreEnabled() {
        return storeEnabled.get();
    }

    @Override
    public void setStoreEnabled(boolean storeEnabled) {
        this.storeEnabled.set(storeEnabled);
    }

    @Override
    public ServerAdapter getServer() {
        return serverAdapter;
    }
}
