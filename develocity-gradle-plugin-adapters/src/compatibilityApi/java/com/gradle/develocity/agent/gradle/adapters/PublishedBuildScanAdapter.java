package com.gradle.develocity.agent.gradle.adapters;

import java.net.URI;

public interface PublishedBuildScanAdapter {

    String getBuildScanId();

    URI getBuildScanUri();

}
