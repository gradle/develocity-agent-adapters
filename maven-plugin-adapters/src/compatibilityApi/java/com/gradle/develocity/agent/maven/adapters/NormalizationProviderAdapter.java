/*
 *
 *  * Copyright 2024-2024 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package com.gradle.develocity.agent.maven.adapters;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public interface NormalizationProviderAdapter {

    void configureNormalization(Context context);

    interface SystemPropertiesNormalization {
        default SystemPropertiesNormalization setIgnoredKeys(String... systemPropertyNames) {
            return setIgnoredKeys(Arrays.asList(systemPropertyNames));
        }

        SystemPropertiesNormalization setIgnoredKeys(List<String> systemPropertyNames);

        default SystemPropertiesNormalization addIgnoredKeys(String... systemPropertyNames) {
            return addIgnoredKeys(Arrays.asList(systemPropertyNames));
        }

        SystemPropertiesNormalization addIgnoredKeys(List<String> systemPropertyNames);
    }

    interface RuntimeClasspathNormalization {
        RuntimeClasspathNormalization setIgnoredFiles(List<String> ignoredFiles);

        default RuntimeClasspathNormalization setIgnoredFiles(String... ignoredFiles) {
            return setIgnoredFiles(Arrays.asList(ignoredFiles));
        }

        RuntimeClasspathNormalization addIgnoredFiles(List<String> ignoredFiles);

        default RuntimeClasspathNormalization addIgnoredFiles(String... ignoredFiles) {
            return addIgnoredFiles(Arrays.asList(ignoredFiles));
        }

        RuntimeClasspathNormalization addPropertiesNormalization(String path, List<String> ignoredProperties);

        default RuntimeClasspathNormalization addPropertiesNormalization(String path, String... ignoredProperties) {
            return addPropertiesNormalization(path, Arrays.asList(ignoredProperties));
        }

        RuntimeClasspathNormalization configureMetaInf(Consumer<MetaInf> action);

        interface MetaInf {
            MetaInf setIgnoredAttributes(List<String> ignoredAttributes);

            default MetaInf setIgnoredAttributes(String... ignoredAttributes) {
                return setIgnoredAttributes(Arrays.asList(ignoredAttributes));
            }

            MetaInf addIgnoredAttributes(List<String> ignoredAttributes);

            default MetaInf addIgnoredAttributes(String... ignoredAttributes) {
                return addIgnoredAttributes(Arrays.asList(ignoredAttributes));
            }

            MetaInf setIgnoredProperties(List<String> ignoredProperties);

            default MetaInf setIgnoredProperties(String... ignoredProperties) {
                return setIgnoredProperties(Arrays.asList(ignoredProperties));
            }

            MetaInf addIgnoredProperties(List<String> ignoredProperties);

            default MetaInf addIgnoredProperties(String... ignoredProperties) {
                return addIgnoredProperties(Arrays.asList(ignoredProperties));
            }

            MetaInf setIgnoreManifest(boolean ignoreManifest);

            MetaInf setIgnoreCompletely(boolean ignoreCompletely);
        }
    }

    interface Context {
        MavenProject getProject();

        MavenSession getSession();

        Context configureRuntimeClasspathNormalization(Consumer<RuntimeClasspathNormalization> action);

        Context configureSystemPropertiesNormalization(Consumer<SystemPropertiesNormalization> action);
    }
}
