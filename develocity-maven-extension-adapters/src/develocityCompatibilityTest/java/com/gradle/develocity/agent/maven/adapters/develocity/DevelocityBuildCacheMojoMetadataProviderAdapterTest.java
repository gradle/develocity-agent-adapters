package com.gradle.develocity.agent.maven.adapters.develocity;

import com.gradle.develocity.agent.maven.adapters.BuildCacheApiAdapter;
import com.gradle.develocity.agent.maven.adapters.MojoMetadataProviderAdapter;
import com.gradle.develocity.agent.maven.api.cache.BuildCacheApi;
import com.gradle.develocity.agent.maven.api.cache.LocalBuildCache;
import com.gradle.develocity.agent.maven.api.cache.MojoMetadataProvider;
import com.gradle.develocity.agent.maven.api.cache.RemoteBuildCache;
import com.gradle.develocity.agent.maven.api.cache.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Stubber;

import java.util.Arrays;

import static com.gradle.develocity.agent.maven.adapters.ActionMockFixtures.doExecuteActionWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DevelocityBuildCacheMojoMetadataProviderAdapterTest {

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

        adapter = new DevelocityBuildCacheApiAdapter(api);
    }

    @Test
    @DisplayName("can access underlying context object using metadata provider")
    void testContextUnderlyingObject() {
        // given
        MojoMetadataProvider.Context ctx = mock();
        doExecuteProviderWith(ctx).when(api).registerMojoMetadataProvider(any());

        // when
        adapter.registerMojoMetadataProvider(MojoMetadataProviderAdapter.Context::getUnderlyingObject);

        // then
        verify(ctx).getUnderlyingObject();
    }

    @Test
    @DisplayName("can access mojo execution using metadata provider")
    void testContextMojoExecution() {
        // given
        MojoMetadataProvider.Context ctx = mock();
        doExecuteProviderWith(ctx).when(api).registerMojoMetadataProvider(any());

        // when
        adapter.registerMojoMetadataProvider(MojoMetadataProviderAdapter.Context::getMojoExecution);

        // then
        verify(ctx).getMojoExecution();
    }

    @Test
    @DisplayName("can access Maven project using metadata provider")
    void testContextProject() {
        // given
        MojoMetadataProvider.Context ctx = mock();
        doExecuteProviderWith(ctx).when(api).registerMojoMetadataProvider(any());

        // when
        adapter.registerMojoMetadataProvider(MojoMetadataProviderAdapter.Context::getProject);

        // then
        verify(ctx).getProject();
    }

    @Test
    @DisplayName("can access Maven session using metadata provider")
    void testContextSession() {
        // given
        MojoMetadataProvider.Context ctx = mock();
        doExecuteProviderWith(ctx).when(api).registerMojoMetadataProvider(any());

        // when
        adapter.registerMojoMetadataProvider(MojoMetadataProviderAdapter.Context::getSession);

        // then
        verify(ctx).getSession();
    }

    @Test
    @DisplayName("can register withPlugin callbacks using metadata provider")
    void testContextWithPlugin() {
        // given
        MojoMetadataProvider.Context ctx = mock();
        doExecuteProviderWith(ctx).when(api).registerMojoMetadataProvider(any());

        // when
        adapter.registerMojoMetadataProvider(c -> c.withPlugin("pluginId", () -> {
        }));

        // then
        verify(ctx).withPlugin(eq("pluginId"), any());
    }

    @Test
    @DisplayName("can register skip properties using metadata provider")
    void testContextSkipIfTrue() {
        // given
        MojoMetadataProvider.Context ctx = mock();
        doExecuteProviderWith(ctx).when(api).registerMojoMetadataProvider(any());

        // when
        adapter.registerMojoMetadataProvider(c -> c.skipIfTrue("prop1", "prop2"));

        // then
        verify(ctx).skipIfTrue(Arrays.asList("prop1", "prop2"));
    }

    @Test
    @DisplayName("can configure inputs using metadata provider")
    void testContextInputs() {
        // given
        MojoMetadataProvider.Context ctx = mock();
        doExecuteProviderWith(ctx).when(api).registerMojoMetadataProvider(any());

        // and
        MojoMetadataProvider.Context.Inputs inputs = mock();
        doExecuteActionWith(inputs).when(ctx).inputs(any());

        // when
        adapter.registerMojoMetadataProvider(c -> c.inputs(i ->
            i.properties("prop1", "prop2")
                .property("prop3", "value3")
                .fileSet("prop4", fs -> {
                })
                .fileSet("prop5", "value5", fs -> {
                })
                .ignore("prop6", "prop7")
        ));

        // then
        verify(inputs).properties("prop1", "prop2");
        verify(inputs).property("prop3", "value3");
        verify(inputs).fileSet(eq("prop4"), any());
        verify(inputs).fileSet(eq("prop5"), eq("value5"), any());
        verify(inputs).ignore("prop6", "prop7");
    }

    @Test
    @DisplayName("can configure outputs using metadata provider")
    void testContextOutputs() {
        // given
        MojoMetadataProvider.Context ctx = mock();
        doExecuteProviderWith(ctx).when(api).registerMojoMetadataProvider(any());

        // and
        MojoMetadataProvider.Context.Outputs outputs = mock();
        doExecuteActionWith(outputs).when(ctx).outputs(any());

        // when
        adapter.registerMojoMetadataProvider(c -> c.outputs(o ->
            o.file("prop1")
                .file("prop2", "value2")
                .directory("prop3")
                .directory("prop4", "value4")
                .cacheable("prop5")
                .notCacheableBecause("prop6")
                .storeEnabled(true)
        ));

        // then
        verify(outputs).file("prop1");
        verify(outputs).file("prop2", "value2");
        verify(outputs).directory("prop3");
        verify(outputs).directory("prop4", "value4");
        verify(outputs).cacheable("prop5");
        verify(outputs).notCacheableBecause("prop6");
        verify(outputs).storeEnabled(true);
    }

    @Test
    @DisplayName("can configure local state using metadata provider")
    void testContextLocalState() {
        // given
        MojoMetadataProvider.Context ctx = mock();
        doExecuteProviderWith(ctx).when(api).registerMojoMetadataProvider(any());

        // and
        MojoMetadataProvider.Context.LocalState localState = mock();
        doExecuteActionWith(localState).when(ctx).localState(any());

        // when
        adapter.registerMojoMetadataProvider(c -> c.localState(s ->
            s.files("prop1", "value1")
                .files("prop2")
        ));

        // then
        verify(localState).files("prop1", "value1");
        verify(localState).files("prop2");
    }

    @Test
    @DisplayName("can configure nested context using metadata provider")
    void testContextNested() {
        // given
        MojoMetadataProvider.Context ctx = mock();
        doExecuteProviderWith(ctx).when(api).registerMojoMetadataProvider(any());

        // when
        adapter.registerMojoMetadataProvider(c -> c.nested("nested", MojoMetadataProviderAdapter.Context::getUnderlyingObject));

        // then
        verify(ctx).nested(eq("nested"), any());
    }

    @Test
    @DisplayName("can iterate over context using metadata provider")
    void testContextIterate() {
        // given
        MojoMetadataProvider.Context ctx = mock();
        doExecuteProviderWith(ctx).when(api).registerMojoMetadataProvider(any());

        // when
        adapter.registerMojoMetadataProvider(c -> c.iterate("iterate", MojoMetadataProviderAdapter.Context::getUnderlyingObject));

        // then
        verify(ctx).iterate(eq("iterate"), any());
    }

    @Test
    @DisplayName("can configure input file sets using metadata provider")
    void testContextFileSet() {
        // given
        MojoMetadataProvider.Context ctx = mock();
        doExecuteProviderWith(ctx).when(api).registerMojoMetadataProvider(any());

        // and
        MojoMetadataProvider.Context.Inputs inputs = mock();
        doExecuteActionWith(inputs).when(ctx).inputs(any());

        // and
        MojoMetadataProvider.Context.FileSet fileSet = mock();
        doExecuteActionWith(fileSet, 1).when(inputs).fileSet(any(), any());

        // when
        adapter.registerMojoMetadataProvider(c -> c.inputs(i -> i.fileSet("fileSet", fs ->
            fs.includesProperty("prop1")
                .include("prop2", "prop3")
                .excludesProperty("prop4")
                .exclude("prop5", "prop6")
                .normalizationStrategy(MojoMetadataProviderAdapter.Context.FileSet.NormalizationStrategy.ABSOLUTE_PATH)
                .emptyDirectoryHandling(MojoMetadataProviderAdapter.Context.FileSet.EmptyDirectoryHandling.IGNORE)
                .lineEndingHandling(MojoMetadataProviderAdapter.Context.FileSet.LineEndingHandling.NORMALIZE)
        )));

        // then
        verify(fileSet).includesProperty("prop1");
        verify(fileSet).include(Arrays.asList("prop2", "prop3"));
        verify(fileSet).excludesProperty("prop4");
        verify(fileSet).exclude(Arrays.asList("prop5", "prop6"));
        verify(fileSet).normalizationStrategy(MojoMetadataProvider.Context.FileSet.NormalizationStrategy.ABSOLUTE_PATH);
        verify(fileSet).emptyDirectoryHandling(MojoMetadataProvider.Context.FileSet.EmptyDirectoryHandling.IGNORE);
        verify(fileSet).lineEndingHandling(MojoMetadataProvider.Context.FileSet.LineEndingHandling.NORMALIZE);
    }

    private static <T> Stubber doExecuteProviderWith(MojoMetadataProvider.Context ctx) {
        return doAnswer(invocation -> {
            MojoMetadataProvider action = invocation.getArgument(0);
            action.provideMetadata(ctx);
            return null;
        });
    }

}
