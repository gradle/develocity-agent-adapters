package com.gradle.develocity.agent.maven.adapters.develocity;

import com.gradle.develocity.agent.maven.adapters.BuildCacheApiAdapter;
import com.gradle.develocity.agent.maven.adapters.LocalBuildCacheAdapter;
import com.gradle.develocity.agent.maven.adapters.RemoteBuildCacheAdapter;
import com.gradle.develocity.agent.maven.api.cache.BuildCacheApi;
import com.gradle.develocity.agent.maven.api.cache.CleanupPolicy;
import com.gradle.develocity.agent.maven.api.cache.Credentials;
import com.gradle.develocity.agent.maven.api.cache.LocalBuildCache;
import com.gradle.develocity.agent.maven.api.cache.RemoteBuildCache;
import com.gradle.develocity.agent.maven.api.cache.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.net.URI;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DevelocityBuildCacheApiAdapterTest {

    private CleanupPolicy cleanupPolicy;
    private LocalBuildCache localCache;
    private Credentials remoteCredentials;
    private Server remoteServer;
    private RemoteBuildCache remoteCache;
    private BuildCacheApi cacheApi;

    private LocalBuildCacheAdapter localCacheAdapter;
    private RemoteBuildCacheAdapter remoteCacheAdapter;
    private BuildCacheApiAdapter cacheAdapter;

    @BeforeEach
    void setup() {
        cleanupPolicy = mock();
        localCache = mock();
        when(localCache.getCleanupPolicy()).thenReturn(cleanupPolicy);
        cacheApi = mock();
        when(cacheApi.getLocal()).thenReturn(localCache);

        remoteCache = mock();
        remoteServer = mock();
        remoteCredentials = mock();
        when(remoteServer.getCredentials()).thenReturn(remoteCredentials);
        when(remoteCache.getServer()).thenReturn(remoteServer);
        when(cacheApi.getRemote()).thenReturn(remoteCache);

        cacheAdapter = new DevelocityBuildCacheApiAdapter(cacheApi);
        localCacheAdapter = cacheAdapter.getLocal();
        remoteCacheAdapter = cacheAdapter.getRemote();
    }

    @Test
    @DisplayName("can enable the local cache using adapter")
    void testLocalEnabled() {
        // when
        localCacheAdapter.setEnabled(true);

        // then
        verify(localCache).setEnabled(true);

        // when
        when(localCache.isEnabled()).thenReturn(true);

        // then
        assertTrue(localCacheAdapter.isEnabled());
    }

    @Test
    @DisplayName("can enable the store in the local cache using adapter")
    void testLocalStoreEnabled() {
        // when
        localCacheAdapter.setStoreEnabled(true);

        // then
        verify(localCache).setStoreEnabled(true);

        // when
        when(localCache.isStoreEnabled()).thenReturn(true);

        // then
        assertTrue(localCacheAdapter.isStoreEnabled());
    }

    @Test
    @DisplayName("can set and retrieve the local cache directory using adapter")
    void testLocalStorageDirectory() {
        //given
        File storageDir = new File("cacheDir");

        // when
        localCacheAdapter.setDirectory(storageDir);

        // then
        verify(localCache).setDirectory(storageDir);

        // when
        when(localCache.getDirectory()).thenReturn(storageDir);

        // then
        assertEquals(storageDir, localCacheAdapter.getDirectory());
    }

    @Test
    @DisplayName("local cleanup policy can be enabled via an adapter")
    void testEnableLocalCleanupPolicy() {
        // when
        localCacheAdapter.getCleanupPolicy().setEnabled(true);

        // then
        verify(cleanupPolicy).setEnabled(true);

        // when
        when(cleanupPolicy.isEnabled()).thenReturn(true);

        // then
        assertTrue(localCacheAdapter.getCleanupPolicy().isEnabled());
    }

    @Test
    @DisplayName("retention period for local cache can be configured via an adapter")
    void testLocalCleanupPolicyRetentionPeriod() {
        //given
        Duration period = Duration.ofDays(1);

        // when
        localCacheAdapter.getCleanupPolicy().setRetentionPeriod(period);

        // then
        verify(cleanupPolicy).setRetentionPeriod(period);

        // when
        when(cleanupPolicy.getRetentionPeriod()).thenReturn(period);

        // then
        assertEquals(period, localCacheAdapter.getCleanupPolicy().getRetentionPeriod());
    }

    @Test
    @DisplayName("cleanup interval for local cache can be configured via an adapter")
    void testLocalCleanupPolicyCleanupInterval() {
        //given
        Duration interval = Duration.ofDays(1);

        // when
        localCacheAdapter.getCleanupPolicy().setCleanupInterval(interval);

        // then
        verify(cleanupPolicy).setCleanupInterval(interval);

        // when
        when(cleanupPolicy.getCleanupInterval()).thenReturn(interval);

        // then
        assertEquals(interval, localCacheAdapter.getCleanupPolicy().getCleanupInterval());
    }

    @Test
    @DisplayName("can enable the remote cache using adapter")
    void testRemoteEnabled() {
        // when
        remoteCacheAdapter.setEnabled(true);

        // then
        verify(remoteCache).setEnabled(true);

        // when
        when(remoteCache.isEnabled()).thenReturn(true);

        // then
        assertTrue(remoteCacheAdapter.isEnabled());
    }

    @Test
    @DisplayName("can enable the store in the remote cache using adapter")
    void testRemoteStoreEnabled() {
        // when
        remoteCacheAdapter.setStoreEnabled(true);

        // then
        verify(remoteCache).setStoreEnabled(true);

        // when
        when(remoteCache.isStoreEnabled()).thenReturn(true);

        // then
        assertTrue(remoteCacheAdapter.isStoreEnabled());
    }

    @Test
    @DisplayName("server ID for remote cache can be configured via an adapter")
    void testRemoteCacheServerId() {
        //given
        String id = "serverId";

        // when
        remoteCacheAdapter.getServer().setServerId(id);

        // then
        verify(remoteServer).setServerId(id);

        // when
        when(remoteServer.getServerId()).thenReturn(id);

        // then
        assertEquals(id, remoteServer.getServerId());
    }

    @Test
    @DisplayName("server URL for remote cache can be configured via an adapter")
    void testRemoteCacheServerUrl() {
        //given
        URI url = URI.create("serverUrl");

        // when
        remoteCacheAdapter.getServer().setUrl(url);

        // then
        verify(remoteServer).setUrl(url);

        // when
        when(remoteServer.getUrl()).thenReturn(url);

        // then
        assertEquals(url, remoteServer.getUrl());
    }

    @Test
    @DisplayName("allow untrusted for remote cache can be configured via an adapter")
    void testRemoteCacheAllowUntrusted() {
        // when
        remoteCacheAdapter.getServer().setAllowUntrusted(true);

        // then
        verify(remoteServer).setAllowUntrusted(true);

        // when
        when(remoteServer.isAllowUntrusted()).thenReturn(false);

        // then
        assertFalse(remoteServer.isAllowUntrusted());
    }

    @Test
    @DisplayName("allow insecure protocol for remote cache can be configured via an adapter")
    void testRemoteCacheAllowInsecureProtocol() {
        // when
        remoteCacheAdapter.getServer().setAllowInsecureProtocol(true);

        // then
        verify(remoteServer).setAllowInsecureProtocol(true);

        // when
        when(remoteServer.isAllowInsecureProtocol()).thenReturn(false);

        // then
        assertFalse(remoteServer.isAllowInsecureProtocol());
    }

    @Test
    @DisplayName("useExpectContinue for remote cache can be configured via an adapter")
    void testRemoteCacheUseExpectContinue() {
        // when
        remoteCacheAdapter.getServer().setUseExpectContinue(true);

        // then
        verify(remoteServer).setUseExpectContinue(true);

        // when
        when(remoteServer.isUseExpectContinue()).thenReturn(false);

        // then
        assertFalse(remoteServer.isUseExpectContinue());
    }

    @Test
    @DisplayName("credentials for remote cache can be configured via an adapter")
    void testRemoteCacheCredentials() {
        // given
        String username = "user";
        String password = "password";

        // when
        remoteCacheAdapter.getServer().getCredentials().setUsername(username);
        remoteCacheAdapter.getServer().getCredentials().setPassword(password);

        // then
        verify(remoteCredentials).setUsername(username);
        verify(remoteCredentials).setPassword(password);

        // when
        when(remoteCredentials.getUsername()).thenReturn(username);
        when(remoteCredentials.getPassword()).thenReturn(password);

        // then
        assertEquals(username, remoteCredentials.getUsername());
        assertEquals(password, remoteCredentials.getPassword());
    }

    @Test
    @DisplayName("clean requirements for cache can be configured via an adapter")
    void testCacheRequireClean() {
        // when
        cacheAdapter.setRequireClean(true);

        // then
        verify(cacheApi).setRequireClean(true);

        // when
        when(cacheAdapter.isRequireClean()).thenReturn(false);

        // then
        assertFalse(cacheApi.isRequireClean());
    }

}
