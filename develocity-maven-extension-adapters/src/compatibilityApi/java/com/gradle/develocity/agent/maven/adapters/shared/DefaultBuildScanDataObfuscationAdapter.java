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

import com.gradle.develocity.agent.maven.adapters.BuildScanDataObfuscationAdapter;

import java.net.InetAddress;
import java.util.List;
import java.util.function.Function;

public class DefaultBuildScanDataObfuscationAdapter implements BuildScanDataObfuscationAdapter {

    private final ObfuscatorConsumer<String, String> usernameObfuscator;
    private final ObfuscatorConsumer<String, String> hostnameObfuscator;
    private final ObfuscatorConsumer<List<InetAddress>, List<String>> ipAddressesObfuscator;

    public DefaultBuildScanDataObfuscationAdapter(
        ObfuscatorConsumer<String, String> usernameObfuscator,
        ObfuscatorConsumer<String, String> hostnameObfuscator,
        ObfuscatorConsumer<List<InetAddress>, List<String>> ipAddressesObfuscator
    ) {
        this.usernameObfuscator = usernameObfuscator;
        this.hostnameObfuscator = hostnameObfuscator;
        this.ipAddressesObfuscator = ipAddressesObfuscator;
    }

    @Override
    public void username(Function<? super String, ? extends String> obfuscator) {
        usernameObfuscator.accept(obfuscator);
    }

    @Override
    public void hostname(Function<? super String, ? extends String> obfuscator) {
        hostnameObfuscator.accept(obfuscator);
    }

    @Override
    public void ipAddresses(Function<? super List<InetAddress>, ? extends List<String>> obfuscator) {
        ipAddressesObfuscator.accept(obfuscator);
    }
}
