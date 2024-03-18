package com.gradle.develocity.agent.maven.adapters.develocity;

import com.gradle.develocity.agent.maven.adapters.BuildCacheApiAdapter;
import com.gradle.develocity.agent.maven.adapters.NormalizationProviderAdapter;
import com.gradle.develocity.agent.maven.api.cache.BuildCacheApi;
import com.gradle.develocity.agent.maven.api.cache.NormalizationProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Stubber;

import java.util.Arrays;

import static com.gradle.develocity.agent.maven.adapters.ActionMockFixtures.doExecuteActionWith;
import static com.gradle.develocity.agent.maven.adapters.develocity.MockFactory.createBuildCacheApi;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DevelocityBuildCacheNormalizationProviderAdapterTest {

    private NormalizationProvider.Context ctx;
    private BuildCacheApiAdapter adapter;

    @BeforeEach
    void setup() {
        BuildCacheApi api = createBuildCacheApi();

        ctx = mock();
        doExecuteProviderWith(ctx).when(api).registerNormalizationProvider(any());

        adapter = new DevelocityBuildCacheApiAdapter(api);
    }

    @Test
    @DisplayName("can access Maven project using normalization provider")
    void testContextProject() {
        // when
        adapter.registerNormalizationProvider(NormalizationProviderAdapter.Context::getProject);

        // then
        verify(ctx).getProject();
    }

    @Test
    @DisplayName("can access Maven session using normalization provider")
    void testContextSession() {
        // when
        adapter.registerNormalizationProvider(NormalizationProviderAdapter.Context::getSession);

        // then
        verify(ctx).getSession();
    }

    @Test
    @DisplayName("can configure sys props normalization using provider")
    void testContextSysPropsNormalization() {
        // given
        NormalizationProvider.SystemPropertiesNormalization sysProps = mock();
        doExecuteActionWith(sysProps).when(ctx).configureSystemPropertiesNormalization(any());

        // when
        adapter.registerNormalizationProvider(c -> c.configureSystemPropertiesNormalization(sp ->
            sp.setIgnoredKeys("prop1", "prop2")
                .addIgnoredKeys("prop3", "prop4")
        ));

        // then
        verify(sysProps).setIgnoredKeys(Arrays.asList("prop1", "prop2"));
        verify(sysProps).addIgnoredKeys(Arrays.asList("prop3", "prop4"));
    }

    @Test
    @DisplayName("can configure runtime normalization using provider")
    void testContextRuntimeNormalization() {
        // given
        NormalizationProvider.RuntimeClasspathNormalization runtime = mock();
        doExecuteActionWith(runtime).when(ctx).configureRuntimeClasspathNormalization(any());

        // when
        adapter.registerNormalizationProvider(c -> c.configureRuntimeClasspathNormalization(rt ->
            rt.setIgnoredFiles("prop1", "prop2")
                .addIgnoredFiles("prop3", "prop4")
                .addPropertiesNormalization("prop4", Arrays.asList("prop5", "prop6"))
                .configureMetaInf(mi -> {
                })
        ));

        // then
        verify(runtime).setIgnoredFiles(Arrays.asList("prop1", "prop2"));
        verify(runtime).addIgnoredFiles(Arrays.asList("prop3", "prop4"));
        verify(runtime).addPropertiesNormalization("prop4", Arrays.asList("prop5", "prop6"));
        verify(runtime).configureMetaInf(any());
    }

    @Test
    @DisplayName("can configure runtime normalization meta information using provider")
    void testContextRuntimeNormalizationMetaInf() {
        // given
        NormalizationProvider.RuntimeClasspathNormalization runtime = mock();
        doExecuteActionWith(runtime).when(ctx).configureRuntimeClasspathNormalization(any());

        // and
        NormalizationProvider.RuntimeClasspathNormalization.MetaInf metaInf = mock();
        doExecuteActionWith(metaInf).when(runtime).configureMetaInf(any());

        // when
        adapter.registerNormalizationProvider(c -> c.configureRuntimeClasspathNormalization(rt -> rt.configureMetaInf(mi ->
            mi.setIgnoredAttributes("prop1", "prop2")
                .addIgnoredAttributes("prop3", "prop4")
                .setIgnoredProperties("prop5", "prop6")
                .addIgnoredProperties("prop7", "prop8")
                .setIgnoreManifest(true)
                .setIgnoreCompletely(false)
        )));

        // then
        verify(metaInf).setIgnoredAttributes(Arrays.asList("prop1", "prop2"));
        verify(metaInf).addIgnoredAttributes(Arrays.asList("prop3", "prop4"));
        verify(metaInf).setIgnoredProperties(Arrays.asList("prop5", "prop6"));
        verify(metaInf).addIgnoredProperties(Arrays.asList("prop7", "prop8"));
        verify(metaInf).setIgnoreManifest(true);
        verify(metaInf).setIgnoreCompletely(false);
    }

    private static Stubber doExecuteProviderWith(NormalizationProvider.Context ctx) {
        return doAnswer(invocation -> {
            NormalizationProvider action = invocation.getArgument(0);
            action.configureNormalization(ctx);
            return null;
        });
    }

}
