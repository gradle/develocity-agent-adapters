package com.gradle.develocity.agent.adapters;

import java.net.URI;

public interface PublishedBuildScanAdapter {

    String getBuildScanId();

    URI getBuildScanUri();

}
