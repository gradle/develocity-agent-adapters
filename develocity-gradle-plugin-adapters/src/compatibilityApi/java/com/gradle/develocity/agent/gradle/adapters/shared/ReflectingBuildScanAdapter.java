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

package com.gradle.develocity.agent.gradle.adapters.shared;

import static com.gradle.develocity.agent.gradle.adapters.internal.ReflectionUtils.invokeMethod;

import com.gradle.develocity.agent.gradle.adapters.BuildResultAdapter;
import com.gradle.develocity.agent.gradle.adapters.BuildScanAdapter;
import com.gradle.develocity.agent.gradle.adapters.PublishedBuildScanAdapter;
import com.gradle.develocity.agent.gradle.adapters.internal.ReflectionProperty;
import com.gradle.develocity.agent.gradle.adapters.internal.ReflectionUtils;

import org.gradle.api.Action;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.util.Collections;
import java.util.List;

public abstract class ReflectingBuildScanAdapter implements BuildScanAdapter {
    protected final Object buildScanExtension;

    public ReflectingBuildScanAdapter(Object buildScanExtension) {
        this.buildScanExtension = buildScanExtension;
    }

    @Override
    public void tag(String tag) {
        invokeMethod(buildScanExtension, "tag", tag);
    }

    @Override
    public void value(String name, String value) {
        invokeMethod(buildScanExtension, "value", name, value);
    }

    @Override
    public void link(String name, String url) {
        invokeMethod(buildScanExtension, "link", name, url);
    }

    @Override
    public void background(Action<? super BuildScanAdapter> action) {
        Action<?> buildScanConfigurationAction = __ -> action.execute(this);
        invokeMethod(buildScanExtension, "background", buildScanConfigurationAction);
    }

    @Override
    public void buildFinished(Action<? super BuildResultAdapter> action) {
        Action<?> buildFinishedAction = buildResult -> action.execute((BuildResultAdapter) () -> getFailuresFromBuildResult(buildResult));
        invokeMethod(buildScanExtension, "buildFinished", buildFinishedAction);
    }

    @Override
    public void buildScanPublished(Action<? super PublishedBuildScanAdapter> action) {
        Action<?> publishedBuildScanAction =scan -> action.execute(new PublishedBuildScanAdapter() {
            @Override
            public String getBuildScanId() {
                return (String) invokeMethod(scan, "getBuildScanId");
            }

            @Override
            public URI getBuildScanUri() {
                return (URI) invokeMethod(scan, "getBuildScanUri");
            }
        });

        invokeMethod(buildScanExtension, "buildScanPublished", publishedBuildScanAction);
    }

    protected List<Throwable> getFailuresFromBuildResult(Object buildResult) {
        if (ReflectionUtils.isMethodSupported(buildResult, "getFailure")) {
            Throwable failure = (Throwable) invokeMethod(buildResult, "getFailure");
            return Collections.singletonList(failure);
        }
        //noinspection unchecked
        return (List<Throwable>) invokeMethod(buildResult, "getFailures");
    }

    @Override
    public @Nullable String getTermsOfUseUrl() {
        return getTermsOfUseUrlProperty().get();
    }

    @Override
    public void setTermsOfUseUrl(String termsOfUseUrl) {
        getTermsOfUseUrlProperty().set(termsOfUseUrl);
    }

    protected abstract ReflectionProperty<String> getTermsOfUseUrlProperty();

    @Override
    public @Nullable String getTermsOfUseAgree() {
        return getTermsOfUseAgreeProperty().get();
    }

    @Override
    public void setTermsOfUseAgree(@Nullable String agree) {
        getTermsOfUseAgreeProperty().set(agree);
    }

    protected abstract ReflectionProperty<String> getTermsOfUseAgreeProperty();

    @Override
    public void setUploadInBackground(boolean value) {
        getUploadInBackgroundProperty().set(value);
    }

    @Override
    public boolean isUploadInBackground() {
        return getUploadInBackgroundProperty().get();
    }

    protected abstract ReflectionProperty<Boolean> getUploadInBackgroundProperty();

    @Override
    public void publishAlways() {
        invokeMethod(buildScanExtension, "publishAlways");
    }

    @Override
    public void publishAlwaysIf(boolean condition) {
        invokeMethod(buildScanExtension, "publishAlwaysIf", condition);
    }

    @Override
    public void publishOnFailure() {
        invokeMethod(buildScanExtension, "publishOnFailure");
    }

    @Override
    public void publishOnFailureIf(boolean condition) {
        invokeMethod(buildScanExtension, "publishOnFailureIf", condition);
    }
}
