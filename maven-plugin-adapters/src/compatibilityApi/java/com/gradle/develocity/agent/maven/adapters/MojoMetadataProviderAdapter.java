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
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.project.MavenProject;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public interface MojoMetadataProviderAdapter {

    void provideMetadata(Context context);

    interface Context {
        Object getUnderlyingObject();

        MojoExecution getMojoExecution();

        MavenProject getProject();

        MavenSession getSession();

        void withPlugin(String artifactId, Runnable action);

        default Context skipIfTrue(String... propertyNames) {
            return skipIfTrue(Arrays.asList(propertyNames));
        }

        Context skipIfTrue(List<String> propertyNames);

        Context inputs(Consumer<? super Inputs> action);

        Context outputs(Consumer<? super Outputs> action);

        Context localState(Consumer<? super LocalState> action);

        Context nested(String propertyName, Consumer<? super Context> action);

        Context iterate(String propertyName, Consumer<? super Context> action);

        interface LocalState {
            LocalState files(String propertyName);

            LocalState files(String propertyName, Object value);
        }

        interface Outputs {
            Outputs file(String propertyName);

            Outputs file(String propertyName, Object file);

            Outputs directory(String propertyName);

            Outputs directory(String propertyName, Object directory);

            Outputs cacheable(String reason);

            Outputs notCacheableBecause(String reason);

            Outputs storeEnabled(boolean enabled);
        }

        interface FileSet {
            FileSet includesProperty(String includePropertyName);

            FileSet include(List<String> includePatterns);

            default FileSet include(String... includePatterns) {
                return include(Arrays.asList(includePatterns));
            }

            FileSet excludesProperty(String excludePropertyName);

            FileSet exclude(List<String> excludePatterns);

            default FileSet exclude(String... excludePatterns) {
                return exclude(Arrays.asList(excludePatterns));
            }

            FileSet normalizationStrategy(NormalizationStrategy normalizationStrategy);

            FileSet emptyDirectoryHandling(EmptyDirectoryHandling emptyDirectoryHandling);

            FileSet lineEndingHandling(LineEndingHandling lineEndingHandling);

            enum LineEndingHandling {
                DEFAULT,
                NORMALIZE
            }

            enum EmptyDirectoryHandling {
                DEFAULT,
                IGNORE
            }

            enum NormalizationStrategy {
                ABSOLUTE_PATH,
                CLASSPATH,
                COMPILE_CLASSPATH,
                IGNORED_PATH,
                NAME_ONLY,
                RELATIVE_PATH
            }
        }

        interface Inputs {
            Inputs properties(String... propertyNames);

            Inputs property(String propertyName, Object value);

            Inputs fileSet(String propertyName, Consumer<FileSet> action);

            Inputs fileSet(String propertyName, Object files, Consumer<FileSet> action);

            Inputs ignore(String... propertyNames);
        }
    }

}
