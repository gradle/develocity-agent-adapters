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

import com.gradle.develocity.agent.gradle.adapters.BuildScanObfuscationAdapter;
import com.gradle.develocity.agent.gradle.adapters.internal.ReflectionUtils;
import com.gradle.develocity.agent.gradle.adapters.shared.ReflectingBuildScanObfuscationAdapter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;

public class BuildScanDataObfuscationAdapter {
    public static BuildScanObfuscationAdapter forBuildScanExtension(Object buildScan) {
        Object obfuscation = ReflectionUtils.invokeMethod(buildScan, "getObfuscation");
        return forObfuscationExtension(obfuscation);
    }

    @VisibleForTesting
    public static @NotNull BuildScanObfuscationAdapter forObfuscationExtension(Object obfuscation) {
        return new ReflectingBuildScanObfuscationAdapter(obfuscation);
    }
}
