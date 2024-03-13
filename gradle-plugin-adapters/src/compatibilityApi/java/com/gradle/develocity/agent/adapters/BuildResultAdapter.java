package com.gradle.develocity.agent.adapters;

import java.util.List;

public interface BuildResultAdapter {

    List<Throwable> getFailures();

}
