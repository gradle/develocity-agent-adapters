package com.gradle.develocity.agent.gradle.adapters.develocity;

import com.gradle.develocity.agent.gradle.adapters.BuildCacheAdapter;
import com.gradle.develocity.agent.gradle.buildcache.DevelocityBuildCache;
import org.jetbrains.annotations.Nullable;

public class DevelocityBuildCacheAdapter implements BuildCacheAdapter {

    private final DevelocityBuildCache buildCache;

    public DevelocityBuildCacheAdapter(Object buildCache) {
        this.buildCache = (DevelocityBuildCache) buildCache;
    }

    @Nullable
    @Override
    public String getServer() {
        return buildCache.getServer();
    }

    @Override
    public void setServer(@Nullable String server) {
        buildCache.setServer(server);
    }

    @Nullable
    @Override
    public String getPath() {
        return buildCache.getPath();
    }

    @Override
    public void setPath(@Nullable String path) {
        buildCache.setPath(path);
    }

    @Nullable
    @Override
    public Boolean getAllowUntrustedServer() {
        return buildCache.getAllowUntrustedServer();
    }

    @Override
    public void setAllowUntrustedServer(boolean allowUntrustedServer) {
        buildCache.setAllowUntrustedServer(allowUntrustedServer);
    }

    @Override
    public boolean getAllowInsecureProtocol() {
        return buildCache.getAllowInsecureProtocol();
    }

    @Override
    public void setAllowInsecureProtocol(boolean allowInsecureProtocol) {
        buildCache.setAllowInsecureProtocol(allowInsecureProtocol);
    }

    @Override
    public void usernameAndPassword(String username, String password) {
        buildCache.usernameAndPassword(username, password);
    }

    @Nullable
    @Override
    public Object getUsernameAndPassword() {
        return buildCache.getUsernameAndPassword();
    }

    @Override
    public boolean getUseExpectContinue() {
        return buildCache.getUseExpectContinue();
    }

    @Override
    public void setUseExpectContinue(boolean useExpectContinue) {
        buildCache.setUseExpectContinue(useExpectContinue);
    }

    @Override
    public boolean isEnabled() {
        return buildCache.isEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {
        buildCache.setEnabled(enabled);
    }

    @Override
    public boolean isPush() {
        return buildCache.isPush();
    }

    @Override
    public void setPush(boolean enabled) {
        buildCache.setPush(enabled);
    }
}
