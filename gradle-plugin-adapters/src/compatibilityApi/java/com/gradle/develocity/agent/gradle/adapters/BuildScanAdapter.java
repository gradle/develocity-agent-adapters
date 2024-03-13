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

package com.gradle.develocity.agent.gradle.adapters;

import org.gradle.api.Action;

import javax.annotation.Nullable;

public interface BuildScanAdapter {

    void background(Action<? super BuildScanAdapter> action);

    void tag(String tag);

    void value(String name, String value);

    void link(String name, String url);

    void buildFinished(Action<? super BuildResultAdapter> action);

    void buildScanPublished(Action<? super PublishedBuildScanAdapter> action);

    void setTermsOfUseUrl(String termsOfServiceUrl);

    @Nullable
    String getTermsOfUseUrl();

    void setTermsOfUseAgree(@Nullable String agree);

    @Nullable
    String getTermsOfUseAgree();

    void setUploadInBackground(boolean uploadInBackground);

    boolean isUploadInBackground();

    void publishAlways();

    void publishAlwaysIf(boolean condition);

    void publishOnFailure();

    void publishOnFailureIf(boolean condition);

    @Nullable
    BuildScanObfuscationAdapter getObfuscation();

    void obfuscation(Action<? super BuildScanObfuscationAdapter> action);

    @Nullable
    BuildScanCaptureAdapter getCapture();

    void capture(Action<? super BuildScanCaptureAdapter> action);
}
