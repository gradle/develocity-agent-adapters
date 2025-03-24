package com.gradle.develocity.agent.gradle.adapters.shared;

import static com.gradle.develocity.agent.gradle.adapters.internal.ReflectionUtils.invokeMethod;

import com.gradle.develocity.agent.gradle.adapters.BuildScanAdapter;
import com.gradle.develocity.agent.gradle.adapters.DevelocityAdapter;
import com.gradle.develocity.agent.gradle.adapters.internal.ReflectionProperty;

import org.gradle.api.Action;
import org.gradle.caching.configuration.AbstractBuildCache;
import org.jetbrains.annotations.Nullable;

public abstract class ReflectingDevelocityAdapter implements DevelocityAdapter {
    protected final Object extension;
    protected final BuildScanAdapter buildScanAdapter;

    protected ReflectingDevelocityAdapter(Object extension, BuildScanAdapter buildScanAdapter) {
        this.extension = extension;
        this.buildScanAdapter = buildScanAdapter;
    }

    @Override
    public BuildScanAdapter getBuildScan() {
        return buildScanAdapter;
    }

    @Override
    public void buildScan(Action<? super BuildScanAdapter> action) {
        action.execute(buildScanAdapter);
    }

    @Override
    public void setServer(@Nullable String server) {
        getServerProperty().set(server);
    }

    @Nullable
    @Override
    public String getServer() {
        return getServerProperty().get();
    }

    protected abstract ReflectionProperty<String> getServerProperty();

    @Override
    public void setProjectId(@Nullable String projectId) {
        getProjectIdProperty().set(projectId);
    }

    @Nullable
    @Override
    public String getProjectId() {
        return getProjectIdProperty().get();
    }

    protected abstract ReflectionProperty<String> getProjectIdProperty();

    @Override
    public void setAllowUntrustedServer(boolean allow) {
        getAllowUntrustedServerProperty().set(allow);
    }

    @Override
    public boolean getAllowUntrustedServer() {
        return getAllowUntrustedServerProperty().get();
    }

    protected abstract ReflectionProperty<Boolean> getAllowUntrustedServerProperty();

    @Override
    public void setAccessKey(@Nullable String accessKey) {
        getAccessKeyProperty().set(accessKey);
    }

    @Nullable
    @Override
    public String getAccessKey() {
        return getAccessKeyProperty().get();
    }

    protected abstract ReflectionProperty<String> getAccessKeyProperty();

    @Nullable
    @Override
    public Class<? extends AbstractBuildCache> getBuildCache() {
        //noinspection unchecked
        return (Class<? extends AbstractBuildCache>) invokeMethod(extension, "getBuildCache");
    }
}
