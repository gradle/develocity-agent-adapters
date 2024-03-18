package com.gradle.develocity.agent.maven.adapters;

import java.util.List;

public interface BuildResultAdapter {

    List<Throwable> getFailures();

}
