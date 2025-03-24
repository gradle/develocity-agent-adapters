package com.gradle.develocity.agent.gradle.adapters.shared;

import static com.gradle.develocity.agent.gradle.adapters.internal.ReflectionUtils.invokeMethod;

import com.gradle.develocity.agent.gradle.adapters.BuildResultAdapter;
import com.gradle.develocity.agent.gradle.adapters.BuildScanAdapter;
import com.gradle.develocity.agent.gradle.adapters.internal.ReflectionProperty;
import com.gradle.develocity.agent.gradle.adapters.internal.ReflectionUtils;

import org.gradle.api.Action;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public abstract class BasicReflectingBuildScanAdapter implements BuildScanAdapter {
    protected final Object buildScanExtension;

    public BasicReflectingBuildScanAdapter(Object buildScanExtension) {
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
        //noinspection Anonymous2MethodRef,Convert2Lambda
        Action<?> buildFinishedAction = buildResult -> action.execute(new BuildResultAdapter() {
            @Override
            public List<Throwable> getFailures() {
                return getFailuresFromBuildResult(buildResult);
            }
        });
        invokeMethod(buildScanExtension, "buildFinished", buildFinishedAction);
    }

    protected List<Throwable> getFailuresFromBuildResult(Object buildResult) {
        if (ReflectionUtils.isMethodSupported(buildResult, "getFailure")) {
            Throwable failure = (Throwable) invokeMethod(buildResult, "getFailure");
            return Collections.singletonList(failure);
        }
        //noinspection unchecked
        return (List<Throwable>) invokeMethod(buildResult, "getFailures");
    }

    protected abstract ReflectionProperty<String> getTermsOfUseUrlProperty();
    protected abstract ReflectionProperty<String> getTermsOfUseAgreeProperty();
    protected abstract ReflectionProperty<Boolean> getUploadInBackgroundProperty();

    @Override
    public @Nullable String getTermsOfUseUrl() {
        return getTermsOfUseUrlProperty().get();
    }

    @Override
    public void setTermsOfUseUrl(String termsOfUseUrl) {
        getTermsOfUseUrlProperty().set(termsOfUseUrl);
    }

    @Override
    public @Nullable String getTermsOfUseAgree() {
        return getTermsOfUseAgreeProperty().get();
    }

    @Override
    public void setTermsOfUseAgree(@Nullable String agree) {
        getTermsOfUseAgreeProperty().set(agree);
    }


    @Override
    public void setUploadInBackground(boolean value) {
        getUploadInBackgroundProperty().set(value);
    }

    @Override
    public boolean isUploadInBackground() {
        return getUploadInBackgroundProperty().get();
    }
}
