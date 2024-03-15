package com.gradle.develocity.agent.maven.adapters.develocity;

import com.gradle.develocity.agent.maven.api.DevelocityApi;
import com.gradle.develocity.agent.maven.api.cache.BuildCacheApi;
import com.gradle.develocity.agent.maven.api.scan.BuildScanApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.gradle.develocity.agent.maven.adapters.develocity.MockFactory.createBuildCacheApi;
import static com.gradle.develocity.agent.maven.adapters.develocity.MockFactory.createBuildScanApi;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DevelocityApiAdapterTest {

    private DevelocityApi api;
    private DevelocityApiAdapter adapter;

    @BeforeEach
    void setup() {
        api = mock();

        BuildScanApi buildScanApi = createBuildScanApi();
        when(api.getBuildScan()).thenReturn(buildScanApi);

        BuildCacheApi buildCacheApi = createBuildCacheApi();
        when(api.getBuildCache()).thenReturn(buildCacheApi);

        adapter = new DevelocityApiAdapter(api);
    }

    @Test
    @DisplayName("can enable the extension using adapter")
    void testEnabled() {
        // when
        adapter.setEnabled(true);

        // then
        verify(api).setEnabled(true);

        // when
        when(api.isEnabled()).thenReturn(true);

        // then
        assertTrue(adapter.isEnabled());
    }

    @Test
    @DisplayName("can set and retrieve the project ID value using adapter")
    void testProjectId() {
        //given
        String projectId = "awesomeProject";

        // when
        adapter.setProjectId(projectId);

        // then
        verify(api).setProjectId(projectId);

        // when
        when(api.getProjectId()).thenReturn(projectId);

        // then
        assertEquals(projectId, adapter.getProjectId());
    }

    @Test
    @DisplayName("can set and retrieve the storage directory using adapter")
    void testStorageDirectory() {
        //given
        Path storageDir = Paths.get("awesomeProject");

        // when
        adapter.setStorageDirectory(storageDir);

        // then
        verify(api).setStorageDirectory(storageDir);

        // when
        when(api.getStorageDirectory()).thenReturn(storageDir);

        // then
        assertEquals(storageDir, adapter.getStorageDirectory());
    }

    @Test
    @DisplayName("can set and retrieve the server value using adapter")
    void testServer() {
        //given
        URI server = URI.create("https://ge-server.com");

        // when
        adapter.setServer(server);

        // then
        verify(api).setServer(server);

        // when
        when(api.getServer()).thenReturn(server.toString());

        // then
        assertEquals(server.toString(), adapter.getServer());
    }

    @Test
    @DisplayName("can set and retrieve the allowUntrustedServer value using adapter")
    void testAllowUntrustedServer() {
        // when
        adapter.setAllowUntrustedServer(true);

        // then
        verify(api).setAllowUntrustedServer(true);

        // when
        when(api.getAllowUntrustedServer()).thenReturn(true);

        // then
        assertTrue(adapter.getAllowUntrustedServer());
    }

    @Test
    @DisplayName("can set and retrieve the access key value using adapter")
    void testAccessKey() {
        // given
        String accessKey = "key";

        // when
        adapter.setAccessKey(accessKey);

        // then
        verify(api).setAccessKey(accessKey);

        // when
        when(api.getAccessKey()).thenReturn(accessKey);

        // then
        assertEquals(accessKey, adapter.getAccessKey());
    }

    @Test
    @DisplayName("can retrieve the build scan API using adapter")
    void testBuildScan() {
        // when
        adapter.getBuildScan();

        // then
        verify(api).getBuildScan();
    }

    @Test
    @DisplayName("can retrieve the build cache API using adapter")
    void testBuildCache() {
        // when
        adapter.getBuildCache();

        // then
        verify(api).getBuildCache();
    }

}
