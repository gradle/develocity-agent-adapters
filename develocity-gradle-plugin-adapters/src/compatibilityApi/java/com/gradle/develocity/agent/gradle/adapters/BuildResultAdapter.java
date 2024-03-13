package com.gradle.develocity.agent.gradle.adapters;

import java.util.List;

public interface BuildResultAdapter {

    List<Throwable> getFailures();

}
