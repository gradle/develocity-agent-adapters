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

package com.gradle.develocity.agent.maven.adapters;

import java.net.URI;

/**
 * @see com.gradle.develocity.agent.maven.api.cache.Server
 * @see com.gradle.maven.extension.api.cache.Server
 */
public interface ServerAdapter {

    String getServerId();

    void setServerId(String serverId);

    URI getUrl();

    default void setUrl(String url) {
        this.setUrl(url == null ? null : URI.create(url));
    }

    void setUrl(URI url);

    boolean isAllowUntrusted();

    void setAllowUntrusted(boolean allowUntrusted);

    boolean isAllowInsecureProtocol();

    void setAllowInsecureProtocol(boolean allowInsecureProtocol);

    boolean isUseExpectContinue();

    void setUseExpectContinue(boolean useExpectContinue);

    CredentialsAdapter getCredentials();

}
