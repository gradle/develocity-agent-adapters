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

import com.gradle.develocity.agent.maven.adapters.CleanupPolicyAdapter;
import com.gradle.develocity.agent.maven.adapters.Property;

import java.time.Duration;

public class DefaultCleanupPolicyAdapter implements CleanupPolicyAdapter {

    private final Property<Boolean> enabled;
    private final Property<Duration> retentionPeriod;
    private final Property<Duration> cleanupInterval;

    public DefaultCleanupPolicyAdapter(
        Property<Boolean> enabled,
        Property<Duration> retentionPeriod,
        Property<Duration> cleanupInterval
    ) {
        this.enabled = enabled;
        this.retentionPeriod = retentionPeriod;
        this.cleanupInterval = cleanupInterval;
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
    public Duration getRetentionPeriod() {
        return retentionPeriod.get();
    }

    @Override
    public void setRetentionPeriod(Duration retentionPeriod) {
        this.retentionPeriod.set(retentionPeriod);
    }

    @Override
    public Duration getCleanupInterval() {
        return cleanupInterval.get();
    }

    @Override
    public void setCleanupInterval(Duration cleanupInterval) {
        this.cleanupInterval.set(cleanupInterval);
    }
}
