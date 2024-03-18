package com.gradle.develocity.agent.maven.adapters;

public interface RemoteBuildCacheAdapter {

    boolean isEnabled();

    void setEnabled(boolean enabled);

    boolean isStoreEnabled();

    void setStoreEnabled(boolean storeEnabled);

    ServerAdapter getServer();

}
