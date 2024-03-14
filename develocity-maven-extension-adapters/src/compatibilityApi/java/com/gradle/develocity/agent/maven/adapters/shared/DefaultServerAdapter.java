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

import com.gradle.develocity.agent.maven.adapters.CredentialsAdapter;
import com.gradle.develocity.agent.maven.adapters.Property;
import com.gradle.develocity.agent.maven.adapters.ServerAdapter;

import java.net.URI;

public class DefaultServerAdapter implements ServerAdapter {

    private final Property<String> serverId;
    private final Property<URI> url;
    private final Property<Boolean> allowUntrusted;
    private final Property<Boolean> allowInsecureProtocol;
    private final Property<Boolean> useExpectContinue;
    private final CredentialsAdapter credentialsAdapter;

    public DefaultServerAdapter(
        Property<String> serverId,
        Property<URI> url,
        Property<Boolean> allowUntrusted,
        Property<Boolean> allowInsecureProtocol,
        Property<Boolean> useExpectContinue,
        CredentialsAdapter credentialsAdapter
    ) {
        this.serverId = serverId;
        this.url = url;
        this.allowUntrusted = allowUntrusted;
        this.allowInsecureProtocol = allowInsecureProtocol;
        this.useExpectContinue = useExpectContinue;
        this.credentialsAdapter = credentialsAdapter;
    }

    @Override
    public String getServerId() {
        return serverId.get();
    }

    @Override
    public void setServerId(String serverId) {
        this.serverId.set(serverId);
    }

    @Override
    public URI getUrl() {
        return url.get();
    }

    @Override
    public void setUrl(URI url) {
        this.url.set(url);
    }

    @Override
    public boolean isAllowUntrusted() {
        return allowUntrusted.get();
    }

    @Override
    public void setAllowUntrusted(boolean allowUntrusted) {
        this.allowUntrusted.set(allowUntrusted);
    }

    @Override
    public boolean isAllowInsecureProtocol() {
        return allowInsecureProtocol.get();
    }

    @Override
    public void setAllowInsecureProtocol(boolean allowInsecureProtocol) {
        this.allowInsecureProtocol.set(allowInsecureProtocol);
    }

    @Override
    public boolean isUseExpectContinue() {
        return useExpectContinue.get();
    }

    @Override
    public void setUseExpectContinue(boolean useExpectContinue) {
        this.useExpectContinue.set(useExpectContinue);
    }

    @Override
    public CredentialsAdapter getCredentials() {
        return credentialsAdapter;
    }
}
