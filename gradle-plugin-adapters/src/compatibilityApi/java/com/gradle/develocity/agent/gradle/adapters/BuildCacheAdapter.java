package com.gradle.develocity.agent.gradle.adapters;

import org.gradle.caching.configuration.BuildCache;

import javax.annotation.Nullable;

public interface BuildCacheAdapter extends BuildCache {

    @Nullable
    String getServer();

    void setServer(@Nullable String server);

    @Nullable
    String getPath();

    void setPath(@Nullable String path);

    @Nullable
    Boolean getAllowUntrustedServer();

    void setAllowUntrustedServer(boolean allowUntrustedServer);

    boolean getAllowInsecureProtocol();

    void setAllowInsecureProtocol(boolean allowInsecureProtocol);

    void usernameAndPassword(String username, String password);

    @Nullable
    Object getUsernameAndPassword();

    boolean getUseExpectContinue();

    void setUseExpectContinue(boolean useExpectContinue);

}
