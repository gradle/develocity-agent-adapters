package com.gradle.develocity.agent.maven.adapters;

import java.net.URI;

public interface PublishedBuildScanAdapter {

    String getBuildScanId();

    URI getBuildScanUri();
}
