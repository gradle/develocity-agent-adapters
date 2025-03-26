package com.gradle.develocity.agent.gradle.adapters.enterprise;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gradle.develocity.agent.gradle.adapters.BuildScanCaptureAdapter;
import com.gradle.scan.plugin.BuildScanExtension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BuildScanCapturePropertyAdapterTest {

    private BuildScanExtension extension;
    private BuildScanCaptureAdapter adapter;

    @BeforeEach
    void setup() {
        extension = mock();
        adapter = BuildScanCaptureExtensionAdapter.forBuildScanCaptureProperty(extension);
    }

    @Test
    @DisplayName("returns fixed values for build and test logging using adapter")
    void testLogging() {
        // setters are ignored (with warning)
        adapter.setBuildLogging(false);
        adapter.setTestLogging(false);

        // then
        assertTrue(adapter.isBuildLogging());
        assertTrue(adapter.isTestLogging());
    }

    @Test
    @DisplayName("can capture task input files value using adapter")
    void testTaskInputFiles() {
        // when
        adapter.setFileFingerprints(true);

        // then
        verify(extension).setCaptureTaskInputFiles(true);

        // when
        when(extension.isCaptureTaskInputFiles()).thenReturn(true);

        // then
        assertTrue(adapter.isFileFingerprints());
    }

}
