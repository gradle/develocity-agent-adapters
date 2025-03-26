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

import static com.gradle.develocity.agent.gradle.adapters.internal.ReflectionUtils.invokeMethod;

import com.gradle.develocity.agent.gradle.adapters.BuildScanCaptureAdapter;
import com.gradle.develocity.agent.gradle.adapters.BuildScanObfuscationAdapter;
import com.gradle.develocity.agent.gradle.adapters.internal.ReflectionProperty;
import com.gradle.develocity.agent.gradle.adapters.shared.ReflectingBuildScanAdapter;

import org.gradle.api.Action;
import org.gradle.api.specs.Spec;
import org.jetbrains.annotations.Nullable;

import java.util.List;

class BuildScanConfigurationAdapter extends ReflectingBuildScanAdapter {
    private final BuildScanCaptureAdapter capture;
    private final BuildScanObfuscationAdapter obfuscation;

    BuildScanConfigurationAdapter(Object buildScan) {
        super(buildScan);
        this.capture = BuildScanCaptureConfigurationAdapter.forBuildScanExtension(buildScan);
        this.obfuscation = BuildScanDataObfuscationConfigurationAdapter.forBuildScanExtension(buildScan);
    }

    @Override
    protected ReflectionProperty<String> getTermsOfUseUrlProperty() {
        return ReflectionProperty.forProperty(buildScanExtension, "getTermsOfUseUrl");
    }

    @Override
    protected ReflectionProperty<String> getTermsOfUseAgreeProperty() {
        return ReflectionProperty.forProperty(buildScanExtension, "getTermsOfUseAgree");
    }

    @Override
    protected ReflectionProperty<Boolean> getUploadInBackgroundProperty() {
        return ReflectionProperty.forProperty(buildScanExtension, "getUploadInBackground");
    }

    @Nullable
    @Override
    public BuildScanObfuscationAdapter getObfuscation() {
        return obfuscation;
    }

    @Override
    public void obfuscation(Action<? super BuildScanObfuscationAdapter> action) {
        action.execute(this.obfuscation);
    }

    @Nullable
    @Override
    public BuildScanCaptureAdapter getCapture() {
        return capture;
    }

    @Override
    public void capture(Action<? super BuildScanCaptureAdapter> action) {
        action.execute(this.capture);
    }

    @Override
    public void publishAlways() {
        publishOnlyIf((Spec<?>) ctx -> true);
    }

    @Override
    public void publishAlwaysIf(boolean condition) {
        Spec<?> publishingContextSpec = ctx -> condition;
        publishOnlyIf(publishingContextSpec);
    }

    @Override
    public void publishOnFailure() {
        publishOnlyIf(ctx -> {
            Object buildResult = invokeMethod(ctx, "getBuildResult");
            List<Throwable> failures = (List<Throwable>) invokeMethod(buildResult, "getFailures");
            return !failures.isEmpty();
        });
    }

    @Override
    public void publishOnFailureIf(boolean condition) {
        publishOnlyIf(ctx -> {
            Object buildResult = invokeMethod(ctx, "getBuildResult");
            List<Throwable> failures = (List<Throwable>) invokeMethod(buildResult, "getFailures");
            return !failures.isEmpty() && condition;
        });
    }

    private void publishOnlyIf(Spec<?> publishingContextSpec) {
        Action<?> publishingConfig = publishing -> invokeMethod(publishing, "onlyIf", publishingContextSpec);
        invokeMethod(buildScanExtension, "publishing", publishingConfig);
    }
}
