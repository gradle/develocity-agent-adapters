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

import com.gradle.develocity.agent.gradle.adapters.BuildScanObfuscationAdapter;
import com.gradle.develocity.agent.gradle.scan.BuildScanDataObfuscationConfiguration;

import java.net.InetAddress;
import java.util.List;
import java.util.function.Function;

final class BuildScanDataObfuscationConfigurationAdapter implements BuildScanObfuscationAdapter {

    private final BuildScanDataObfuscationConfiguration obfuscation;

    BuildScanDataObfuscationConfigurationAdapter(BuildScanDataObfuscationConfiguration obfuscation) {
        this.obfuscation = obfuscation;
    }

    @Override
    public void username(Function<? super String, ? extends String> obfuscator) {
        obfuscation.username(obfuscator);
    }

    @Override
    public void hostname(Function<? super String, ? extends String> obfuscator) {
        obfuscation.hostname(obfuscator);
    }

    @Override
    public void ipAddresses(Function<? super List<InetAddress>, ? extends List<String>> obfuscator) {
        obfuscation.ipAddresses(obfuscator);
    }
}
