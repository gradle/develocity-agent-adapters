package com.gradle.develocity.agent.maven.adapters.develocity;

import com.gradle.develocity.agent.maven.api.cache.BuildCacheApi;
import com.gradle.develocity.agent.maven.api.cache.LocalBuildCache;
import com.gradle.develocity.agent.maven.api.cache.RemoteBuildCache;
import com.gradle.develocity.agent.maven.api.cache.Server;
import com.gradle.develocity.agent.maven.api.scan.BuildScanApi;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MockFactory {

    private MockFactory() {
    }

    static BuildScanApi createBuildScanApi() {
        BuildScanApi buildScanApi = mock();
        when(buildScanApi.getObfuscation()).thenReturn(mock());
        when(buildScanApi.getCapture()).thenReturn(mock());
        return buildScanApi;
    }

    static BuildCacheApi createBuildCacheApi() {
        LocalBuildCache local = mock();
        when(local.getCleanupPolicy()).thenReturn(mock());

        RemoteBuildCache remote = mock();
        Server server = mock();
        when(server.getCredentials()).thenReturn(mock());
        when(remote.getServer()).thenReturn(server);

        BuildCacheApi buildCacheApi = mock();
        when(buildCacheApi.getLocal()).thenReturn(local);
        when(buildCacheApi.getRemote()).thenReturn(remote);

        return buildCacheApi;
    }
}
