package com.gradle.develocity.agent.gradle.adapters.shared;

import static com.gradle.develocity.agent.gradle.adapters.internal.ReflectionUtils.invokeMethod;

import com.gradle.develocity.agent.gradle.adapters.BuildScanAdapter;
import com.gradle.develocity.agent.gradle.adapters.internal.ReflectionProperty;

import org.jetbrains.annotations.Nullable;

public abstract class BasicReflectingBuildScanAdapter implements BuildScanAdapter {
    private final Object buildScanExtension;

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
