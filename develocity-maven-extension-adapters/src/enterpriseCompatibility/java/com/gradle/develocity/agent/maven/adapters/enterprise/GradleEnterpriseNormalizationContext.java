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

package com.gradle.develocity.agent.maven.adapters.enterprise;

import com.gradle.develocity.agent.maven.adapters.NormalizationProviderAdapter;
import com.gradle.maven.extension.api.cache.NormalizationProvider;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;

import java.util.List;
import java.util.function.Consumer;

class GradleEnterpriseNormalizationContext implements NormalizationProviderAdapter.Context {
    private final NormalizationProvider.Context ctx;

    GradleEnterpriseNormalizationContext(NormalizationProvider.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public MavenProject getProject() {
        return ctx.getProject();
    }

    @Override
    public MavenSession getSession() {
        return ctx.getSession();
    }

    @Override
    public NormalizationProviderAdapter.Context configureRuntimeClasspathNormalization(Consumer<NormalizationProviderAdapter.RuntimeClasspathNormalization> action) {
        ctx.configureRuntimeClasspathNormalization(normalization -> action.accept(new RuntimeClasspathNormalization(normalization)));
        return this;
    }

    @Override
    public NormalizationProviderAdapter.Context configureSystemPropertiesNormalization(Consumer<NormalizationProviderAdapter.SystemPropertiesNormalization> action) {
        ctx.configureSystemPropertiesNormalization(normalization -> action.accept(new SystemPropertiesNormalization(normalization)));
        return this;
    }

    private static class RuntimeClasspathNormalization implements NormalizationProviderAdapter.RuntimeClasspathNormalization {

        private final NormalizationProvider.RuntimeClasspathNormalization normalization;

        private RuntimeClasspathNormalization(NormalizationProvider.RuntimeClasspathNormalization normalization) {
            this.normalization = normalization;
        }

        @Override
        public NormalizationProviderAdapter.RuntimeClasspathNormalization setIgnoredFiles(List<String> ignoredFiles) {
            normalization.setIgnoredFiles(ignoredFiles);
            return this;
        }

        @Override
        public NormalizationProviderAdapter.RuntimeClasspathNormalization addIgnoredFiles(List<String> ignoredFiles) {
            normalization.addIgnoredFiles(ignoredFiles);
            return this;
        }

        @Override
        public NormalizationProviderAdapter.RuntimeClasspathNormalization addPropertiesNormalization(String path, List<String> ignoredProperties) {
            normalization.addPropertiesNormalization(path, ignoredProperties);
            return this;
        }

        @Override
        public NormalizationProviderAdapter.RuntimeClasspathNormalization configureMetaInf(Consumer<NormalizationProviderAdapter.RuntimeClasspathNormalization.MetaInf> action) {
            normalization.configureMetaInf(metaInf -> action.accept(new MetaInf(metaInf)));
            return this;
        }

        private static class MetaInf implements NormalizationProviderAdapter.RuntimeClasspathNormalization.MetaInf {

            private final NormalizationProvider.RuntimeClasspathNormalization.MetaInf metaInf;

            MetaInf(NormalizationProvider.RuntimeClasspathNormalization.MetaInf metaInf) {
                this.metaInf = metaInf;
            }

            @Override
            public NormalizationProviderAdapter.RuntimeClasspathNormalization.MetaInf setIgnoredAttributes(List<String> ignoredAttributes) {
                metaInf.setIgnoredAttributes(ignoredAttributes);
                return this;
            }

            @Override
            public NormalizationProviderAdapter.RuntimeClasspathNormalization.MetaInf addIgnoredAttributes(List<String> ignoredAttributes) {
                metaInf.addIgnoredAttributes(ignoredAttributes);
                return this;
            }

            @Override
            public NormalizationProviderAdapter.RuntimeClasspathNormalization.MetaInf setIgnoredProperties(List<String> ignoredProperties) {
                metaInf.setIgnoredProperties(ignoredProperties);
                return this;
            }

            @Override
            public NormalizationProviderAdapter.RuntimeClasspathNormalization.MetaInf addIgnoredProperties(List<String> ignoredProperties) {
                metaInf.addIgnoredProperties(ignoredProperties);
                return this;
            }

            @Override
            public NormalizationProviderAdapter.RuntimeClasspathNormalization.MetaInf setIgnoreManifest(boolean ignoreManifest) {
                metaInf.setIgnoreManifest(ignoreManifest);
                return this;
            }

            @Override
            public NormalizationProviderAdapter.RuntimeClasspathNormalization.MetaInf setIgnoreCompletely(boolean ignoreCompletely) {
                metaInf.setIgnoreCompletely(ignoreCompletely);
                return this;
            }
        }
    }

    private static class SystemPropertiesNormalization implements NormalizationProviderAdapter.SystemPropertiesNormalization {
        private final NormalizationProvider.SystemPropertiesNormalization normalization;

        public SystemPropertiesNormalization(NormalizationProvider.SystemPropertiesNormalization normalization) {
            this.normalization = normalization;
        }

        @Override
        public NormalizationProviderAdapter.SystemPropertiesNormalization setIgnoredKeys(List<String> systemPropertyNames) {
            normalization.setIgnoredKeys(systemPropertyNames);
            return this;
        }

        @Override
        public NormalizationProviderAdapter.SystemPropertiesNormalization addIgnoredKeys(List<String> systemPropertyNames) {
            normalization.addIgnoredKeys(systemPropertyNames);
            return this;
        }
    }
}
