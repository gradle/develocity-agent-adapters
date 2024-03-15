package com.gradle.develocity.agent.maven.adapters.enterprise;

import com.gradle.develocity.agent.maven.adapters.BuildCacheApiAdapter;
import com.gradle.develocity.agent.maven.adapters.NormalizationProviderAdapter;
import com.gradle.maven.extension.api.cache.BuildCacheApi;
import com.gradle.maven.extension.api.cache.LocalBuildCache;
import com.gradle.maven.extension.api.cache.NormalizationProvider;
import com.gradle.maven.extension.api.cache.RemoteBuildCache;
import com.gradle.maven.extension.api.cache.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Stubber;

import java.util.Arrays;

import static com.gradle.develocity.agent.maven.adapters.ActionMockFixtures.doExecuteActionWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GradleEnterpriseBuildCacheNormalizationProviderAdapterTest {

    private BuildCacheApi api;
    private BuildCacheApiAdapter adapter;

    @BeforeEach
    void setup() {
        LocalBuildCache localCache = mock();
        when(localCache.getCleanupPolicy()).thenReturn(mock());

        Server remoteServer = mock();
        when(remoteServer.getCredentials()).thenReturn(mock());
        RemoteBuildCache remoteCache = mock();
        when(remoteCache.getServer()).thenReturn(remoteServer);

        api = mock();
        when(api.getLocal()).thenReturn(localCache);
        when(api.getRemote()).thenReturn(remoteCache);

        adapter = new GradleEnterpriseBuildCacheApiAdapter(api);
    }

    @Test
    @DisplayName("can access Maven project using normalization provider")
    void testContextProject() {
        // given
        NormalizationProvider.Context ctx = mock();
        doExecuteProviderWith(ctx).when(api).registerNormalizationProvider(any());

        // when
        adapter.registerNormalizationProvider(NormalizationProviderAdapter.Context::getProject);

        // then
        verify(ctx).getProject();
    }

    @Test
    @DisplayName("can access Maven session using normalization provider")
    void testContextSession() {
        // given
        NormalizationProvider.Context ctx = mock();
        doExecuteProviderWith(ctx).when(api).registerNormalizationProvider(any());

        // when
        adapter.registerNormalizationProvider(NormalizationProviderAdapter.Context::getSession);

        // then
        verify(ctx).getSession();
    }

    @Test
    @DisplayName("can configure sys props normalization using provider")
    void testContextSysPropsNormalization() {
        // given
        NormalizationProvider.Context ctx = mock();
        doExecuteProviderWith(ctx).when(api).registerNormalizationProvider(any());

        // and
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
        NormalizationProvider.Context ctx = mock();
        doExecuteProviderWith(ctx).when(api).registerNormalizationProvider(any());

        // and
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
        NormalizationProvider.Context ctx = mock();
        doExecuteProviderWith(ctx).when(api).registerNormalizationProvider(any());

        // and
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

    private static <T> Stubber doExecuteProviderWith(NormalizationProvider.Context ctx) {
        return doAnswer(invocation -> {
            NormalizationProvider action = invocation.getArgument(0);
            action.configureNormalization(ctx);
            return null;
        });
    }

}
