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

import com.gradle.develocity.agent.gradle.adapters.BuildResultAdapter;
import com.gradle.develocity.agent.gradle.adapters.BuildScanAdapter;
import com.gradle.develocity.agent.gradle.adapters.BuildScanCaptureAdapter;
import com.gradle.develocity.agent.gradle.adapters.BuildScanObfuscationAdapter;
import com.gradle.develocity.agent.gradle.adapters.PublishedBuildScanAdapter;
import com.gradle.develocity.agent.gradle.adapters.internal.ReflectionProperty;
import com.gradle.develocity.agent.gradle.adapters.shared.BasicReflectingBuildScanAdapter;
import com.gradle.develocity.agent.gradle.scan.BuildScanConfiguration;
import org.gradle.api.Action;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.util.List;

class BuildScanConfigurationAdapter extends BasicReflectingBuildScanAdapter {
    private final BuildScanConfiguration buildScan;

    private final BuildScanCaptureAdapter capture;
    private final BuildScanObfuscationAdapter obfuscation;

    BuildScanConfigurationAdapter(BuildScanConfiguration buildScan) {
        super(buildScan);
        this.buildScan = buildScan;
        this.capture = BuildScanCaptureConfigurationAdapter.forBuildScanExtension(buildScan);
        this.obfuscation = BuildScanDataObfuscationConfigurationAdapter.forBuildScanExtension(buildScan);
    }

    @Override
    public void background(Action<? super BuildScanAdapter> action) {
        buildScan.background(__ -> action.execute(this));
    }

    @Override
    public void buildFinished(Action<? super BuildResultAdapter> action) {
        //noinspection Anonymous2MethodRef,Convert2Lambda
        buildScan.buildFinished(buildResult -> action.execute(new BuildResultAdapter() {
            @Override
            public List<Throwable> getFailures() {
                return buildResult.getFailures();
            }
        }));
    }

    @Override
    public void buildScanPublished(Action<? super PublishedBuildScanAdapter> action) {
        buildScan.buildScanPublished(scan -> action.execute(new PublishedBuildScanAdapter() {
            @Override
            public String getBuildScanId() {
                return scan.getBuildScanId();
            }

            @Override
            public URI getBuildScanUri() {
                return scan.getBuildScanUri();
            }
        }));
    }

    @Override
    protected ReflectionProperty<String> getTermsOfUseUrlProperty() {
        return ReflectionProperty.forProperty(buildScan, "getTermsOfUseUrl");
    }

    @Override
    protected ReflectionProperty<String> getTermsOfUseAgreeProperty() {
        return ReflectionProperty.forProperty(buildScan, "getTermsOfUseAgree");
    }

    @Override
    protected ReflectionProperty<Boolean> getUploadInBackgroundProperty() {
        return ReflectionProperty.forProperty(buildScan, "getUploadInBackground");
    }

    @Override
    public void publishAlways() {
        buildScan.publishing(publishing -> publishing.onlyIf(ctx -> true));
    }

    @Override
    public void publishAlwaysIf(boolean condition) {
        buildScan.publishing(publishing -> publishing.onlyIf(ctx -> condition));
    }

    @Override
    public void publishOnFailure() {
        buildScan.publishing(publishing -> publishing.onlyIf(ctx -> !ctx.getBuildResult().getFailures().isEmpty()));
    }

    @Override
    public void publishOnFailureIf(boolean condition) {
        buildScan.publishing(publishing -> publishing.onlyIf(ctx -> !ctx.getBuildResult().getFailures().isEmpty() && condition));
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
}
