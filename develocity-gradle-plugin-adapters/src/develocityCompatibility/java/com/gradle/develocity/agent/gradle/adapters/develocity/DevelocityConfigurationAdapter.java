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
import com.gradle.develocity.agent.gradle.adapters.internal.ReflectionProperty;
import com.gradle.develocity.agent.gradle.adapters.shared.ReflectingDevelocityAdapter;

import static com.gradle.develocity.agent.gradle.adapters.internal.AdapterTypeUtils.checkIsDevelocityConfiguration;
import static com.gradle.develocity.agent.gradle.adapters.internal.ReflectionUtils.invokeMethod;

public class DevelocityConfigurationAdapter extends ReflectingDevelocityAdapter implements DevelocityAdapter {

    public DevelocityConfigurationAdapter(Object configuration) {
        super(checkDevelocityConfiguration(configuration), createBuildScanAdapter(configuration));
    }

    private static Object checkDevelocityConfiguration(Object configuration) {
        checkIsDevelocityConfiguration(configuration);
        return configuration;
    }

    private static BuildScanAdapter createBuildScanAdapter(Object configuration) {
        Object rawBuildScan = invokeMethod(configuration, "getBuildScan");
        return new BuildScanConfigurationAdapter(rawBuildScan);
    }

    @Override
    protected ReflectionProperty<String> getServerProperty() {
        return ReflectionProperty.forProperty(extension, "getServer");
    }

    @Override
    protected ReflectionProperty<String> getProjectIdProperty() {
        return ReflectionProperty.forProperty(extension, "getProjectId");
    }

    @Override
    protected ReflectionProperty<Boolean> getAllowUntrustedServerProperty() {
        return ReflectionProperty.forProperty(extension, "getAllowUntrustedServer", true);
    }

    @Override
    protected ReflectionProperty<String> getAccessKeyProperty() {
        return ReflectionProperty.forProperty(extension, "getAccessKey");
    }
}
