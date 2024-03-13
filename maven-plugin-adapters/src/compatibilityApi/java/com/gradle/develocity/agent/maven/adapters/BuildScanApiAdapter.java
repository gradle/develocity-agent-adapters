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
import java.util.function.Consumer;

public interface BuildScanApiAdapter {

    void tag(String tag);

    void value(String name, String value);

    void link(String name, String url);

    void background(Consumer<? super BuildScanApiAdapter> action);

    void buildFinished(Consumer<? super BuildResultAdapter> action);

    void buildScanPublished(Consumer<? super PublishedBuildScanAdapter> action);

    void setTermsOfUseUrl(String termsOfServiceUrl);

    String getTermsOfUseUrl();

    void setTermsOfUseAgree(String agree);

    String getTermsOfUseAgree();

    default void setServer(String url) {
        setServer(url == null ? null : URI.create(url));
    }

    void setServer(URI url);

    String getServer();

    void setAllowUntrustedServer(boolean allow);

    boolean getAllowUntrustedServer();

    void publishAlways();

    void publishAlwaysIf(boolean condition);

    void publishOnFailure();

    void publishOnFailureIf(boolean condition);

    void publishOnDemand();

    void setUploadInBackground(boolean uploadInBackground);

    boolean isUploadInBackground();

    void executeOnce(String identifier, Consumer<? super BuildScanApiAdapter> action);

    BuildScanDataObfuscationAdapter getObfuscation();

    default void obfuscation(Consumer<? super BuildScanDataObfuscationAdapter> action) {
        action.accept(getObfuscation());
    }

    BuildScanCaptureAdapter getCapture();

    default void capture(Consumer<? super BuildScanCaptureAdapter> action) {
        action.accept(getCapture());
    }

}
