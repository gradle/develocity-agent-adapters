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
import com.gradle.develocity.agent.gradle.adapters.internal.ReflectionProperty;
import com.gradle.develocity.agent.gradle.adapters.shared.ReflectingDevelocityAdapter;

import static com.gradle.develocity.agent.gradle.adapters.internal.AdapterTypeUtils.checkIsGradleEnterpriseExtension;
import static com.gradle.develocity.agent.gradle.adapters.internal.ReflectionUtils.invokeMethod;

public class GradleEnterpriseExtensionAdapter extends ReflectingDevelocityAdapter implements DevelocityAdapter {

    public GradleEnterpriseExtensionAdapter(Object extension) {
        super(checkGradleEnterpriseExtension(extension), createBuildScanAdapter(extension));
    }

    private static Object checkGradleEnterpriseExtension(Object extension) {
        checkIsGradleEnterpriseExtension(extension);
        return extension;
    }

    private static BuildScanAdapter createBuildScanAdapter(Object extension) {
        Object rawBuildScan = invokeMethod(extension, "getBuildScan");
        return new BuildScanExtensionAdapter(rawBuildScan);
    }

    protected ReflectionProperty<String> getServerProperty() {
        return ReflectionProperty.forGetterAndSetter(extension, "getServer", "setServer");
    }

    protected ReflectionProperty<String> getProjectIdProperty() {
        return ReflectionProperty.forGetterAndSetter(extension, "getProjectId", "setProjectId");
    }

    protected ReflectionProperty<Boolean> getAllowUntrustedServerProperty() {
        return ReflectionProperty.forGetterAndSetter(extension, "getAllowUntrustedServer", "setAllowUntrustedServer");
    }

    protected ReflectionProperty<String> getAccessKeyProperty() {
        return ReflectionProperty.forGetterAndSetter(extension, "getAccessKey", "setAccessKey");
    }
}
