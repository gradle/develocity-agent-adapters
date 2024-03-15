package com.gradle.develocity.agent.maven.adapters.enterprise;

import com.gradle.develocity.agent.maven.adapters.BuildScanCaptureAdapter;
import com.gradle.maven.extension.api.scan.BuildScanApi;
import com.gradle.maven.extension.api.scan.BuildScanCaptureSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gradle.develocity.agent.maven.adapters.enterprise.MockFactory.createBuildScanApi;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GradleEnterpriseBuildScanCaptureSettingsAdapterTest {

    private BuildScanCaptureSettings capture;
    private BuildScanCaptureAdapter adapter;

    @BeforeEach
    void setup() {
        capture = mock();
        BuildScanApi buildScanApi = createBuildScanApi();
        when(buildScanApi.getCapture()).thenReturn(capture);
        adapter = new GradleEnterpriseBuildScanApiAdapter(buildScanApi).getCapture();
    }

    @Test
    @DisplayName("can capture build logging value using adapter")
    void testBuildLogging() {
        // when
        adapter.setBuildLogging(true);

        // then
        verify(capture).setBuildLogging(true);

        // when
        when(capture.isBuildLogging()).thenReturn(true);

        // then
        assertTrue(adapter.isBuildLogging());
    }

    @Test
    @DisplayName("can capture test logging value using adapter")
    void testTestLogging() {
        // when
        adapter.setTestLogging(true);

        // then
        verify(capture).setTestLogging(true);

        // when
        when(capture.isTestLogging()).thenReturn(true);

        // then
        assertTrue(adapter.isTestLogging());
    }

    @Test
    @DisplayName("can capture file fingerprints value using adapter")
    void testFileFingerprints() {
        // when
        adapter.setGoalInputFiles(true);

        // then
        verify(capture).setGoalInputFiles(true);

        // when
        when(capture.isGoalInputFiles()).thenReturn(true);

        // then
        assertTrue(adapter.isGoalInputFiles());
    }

}
