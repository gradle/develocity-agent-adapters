package com.gradle.develocity.agent.maven.adapters.develocity;

import com.gradle.develocity.agent.maven.adapters.ActionMockFixtures.ArgCapturingAction;
import com.gradle.develocity.agent.maven.adapters.BuildResultAdapter;
import com.gradle.develocity.agent.maven.adapters.PublishedBuildScanAdapter;
import com.gradle.develocity.agent.maven.api.scan.BuildResult;
import com.gradle.develocity.agent.maven.api.scan.BuildScanApi;
import com.gradle.develocity.agent.maven.api.scan.BuildScanCaptureSettings;
import com.gradle.develocity.agent.maven.api.scan.BuildScanDataObfuscation;
import com.gradle.develocity.agent.maven.api.scan.BuildScanPublishing;
import com.gradle.develocity.agent.maven.api.scan.PublishedBuildScan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.Collections;
import java.util.function.Predicate;

import static com.gradle.develocity.agent.maven.adapters.ActionMockFixtures.doExecuteActionWith;
import static com.gradle.develocity.agent.maven.adapters.develocity.MockFactory.createBuildScanApi;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DevelocityBuildScanApiAdapterTest {

    private BuildScanApi api;
    private DevelocityBuildScanApiAdapter adapter;

    @BeforeEach
    void setup() {
        api = createBuildScanApi();
        adapter = new DevelocityBuildScanApiAdapter(api);
    }

    @Test
    @DisplayName("tags can be set via an adapter")
    void testTag() {
        //given
        String tag = "tag";

        // when
        adapter.tag(tag);

        // then
        verify(api).tag(tag);
    }

    @Test
    @DisplayName("values can be set via an adapter")
    void testValue() {
        //given
        String name = "name";
        String value = "value";

        // when
        adapter.value(name, value);

        // then
        verify(api).value(name, value);
    }

    @Test
    @DisplayName("links can be set via an adapter")
    void testLink() {
        //given
        String name = "name";
        String value = "https://value.com";

        // when
        adapter.link(name, value);

        // then
        verify(api).link(name, value);
    }

    @Test
    @DisplayName("terms of use URL can be set via an adapter")
    void testTermsOfUseUrl() {
        //given
        String value = "https://value.com";

        // when
        adapter.setTermsOfUseUrl(value);

        // then
        verify(api).setTermsOfUseUrl(value);

        // when
        when(api.getTermsOfUseUrl()).thenReturn(value);

        // then
        assertEquals(value, adapter.getTermsOfUseUrl());
    }

    @Test
    @DisplayName("terms of use agreement can be set via an adapter")
    void testTermsOfUseAgree() {
        //given
        String value = "no";

        // when
        adapter.setTermsOfUseAgree(value);

        // then
        verify(api).setTermsOfUseAgree(value);

        // when
        when(api.getTermsOfUseAgree()).thenReturn(value);

        // then
        assertEquals(value, adapter.getTermsOfUseAgree());
    }

    @Test
    @DisplayName("can set and retrieve the server value using adapter")
    void testServer() {
        //given
        URI server = URI.create("https://ge-server.com");

        // when
        adapter.setServer(server);

        // then
        verify(api).setServer(server);

        // when
        when(api.getServer()).thenReturn(server.toString());

        // then
        assertEquals(server.toString(), adapter.getServer());
    }

    @Test
    @DisplayName("can set and retrieve the allowUntrustedServer value using adapter")
    void testAllowUntrustedServer() {
        // when
        adapter.setAllowUntrustedServer(true);

        // then
        verify(api).setAllowUntrustedServer(true);

        // when
        when(api.getAllowUntrustedServer()).thenReturn(true);

        // then
        assertTrue(adapter.getAllowUntrustedServer());
    }

    @Test
    @DisplayName("background action can be configured via an adapter")
    void testBackgroundAction() {
        //given
        doExecuteActionWith(api).when(api).background(any());

        // when
        adapter.background(b -> {
            b.setTermsOfUseUrl("other url");
            b.setTermsOfUseAgree("no");
        });

        // then
        verify(api).setTermsOfUseUrl("other url");
        verify(api).setTermsOfUseAgree("no");
    }

    @Test
    @DisplayName("build finished action can be configured via an adapter using the new build result model")
    void testBuildFinishedAction() {
        // given
        Throwable failure = new RuntimeException("New build failure!");
        BuildResult buildResult = mock();
        when(buildResult.getFailures()).thenReturn(Collections.singletonList(failure));

        // and
        doExecuteActionWith(buildResult).when(api).buildFinished(any());

        // when
        ArgCapturingAction<BuildResultAdapter> capturedNewBuildResult = new ArgCapturingAction<>();
        adapter.buildFinished(capturedNewBuildResult);

        // then
        assertEquals(Collections.singletonList(failure), capturedNewBuildResult.getValue().getFailures());
    }

    @Test
    @DisplayName("build scan published action can be configured via an adapter using the new scan model")
    void testBuildScanPublishedAction() {
        // given
        PublishedBuildScan publishedScan = mock();
        when(publishedScan.getBuildScanId()).thenReturn("scanId");
        doExecuteActionWith(publishedScan).when(api).buildScanPublished(any());

        // when
        ArgCapturingAction<PublishedBuildScanAdapter> capturedPublishedBuildScan = new ArgCapturingAction<>();
        adapter.buildScanPublished(capturedPublishedBuildScan);

        // then
        assertEquals("scanId", capturedPublishedBuildScan.getValue().getBuildScanId());
    }

    @Test
    @DisplayName("publishing always can be configured on the Develocity configuration via an adapter")
    void testPublishAlways() {
        // given
        ArgumentCaptor<Predicate<BuildScanPublishing.PublishingContext>> specCaptor = withCapturedPublishingSpec(api);

        // when
        adapter.publishAlways();

        // then
        assertTrue(specCaptor.getValue().test(publishingCtxForSuccessfulBuild()));
        assertTrue(specCaptor.getValue().test(publishingCtxForFailingBuild()));
    }

    @Test
    @DisplayName("conditional publishing always can be configured on the Develocity configuration via an adapter")
    void testPublishAlwaysIf() {
        // given
        ArgumentCaptor<Predicate<BuildScanPublishing.PublishingContext>> specCaptor = withCapturedPublishingSpec(api);

        // when
        adapter.publishAlwaysIf(true);

        // then
        assertTrue(specCaptor.getValue().test(publishingCtxForSuccessfulBuild()));
        assertTrue(specCaptor.getValue().test(publishingCtxForFailingBuild()));

        // when
        adapter.publishAlwaysIf(false);

        // then
        assertFalse(specCaptor.getValue().test(publishingCtxForSuccessfulBuild()));
        assertFalse(specCaptor.getValue().test(publishingCtxForFailingBuild()));
    }

    @Test
    @DisplayName("publishing on failure can be configured on the Develocity configuration via an adapter")
    void testPublishOnFailure() {
        // given
        ArgumentCaptor<Predicate<BuildScanPublishing.PublishingContext>> specCaptor = withCapturedPublishingSpec(api);

        // when
        adapter.publishOnFailure();

        // then
        assertFalse(specCaptor.getValue().test(publishingCtxForSuccessfulBuild()));
        assertTrue(specCaptor.getValue().test(publishingCtxForFailingBuild()));
    }

    @Test
    @DisplayName("conditional publishing on failure can be configured on the Develocity configuration via an adapter")
    void testPublishOnFailureIf() {
        // given
        ArgumentCaptor<Predicate<BuildScanPublishing.PublishingContext>> specCaptor = withCapturedPublishingSpec(api);

        // when
        adapter.publishOnFailureIf(true);

        // then
        assertFalse(specCaptor.getValue().test(publishingCtxForSuccessfulBuild()));
        assertTrue(specCaptor.getValue().test(publishingCtxForFailingBuild()));

        // when
        adapter.publishOnFailureIf(false);

        // then
        assertFalse(specCaptor.getValue().test(publishingCtxForSuccessfulBuild()));
        assertFalse(specCaptor.getValue().test(publishingCtxForFailingBuild()));
    }

    @Test
    @DisplayName("conditional publishing on failure can be configured on the Develocity configuration via an adapter")
    void testPublishOnDemand() {
        // given
        ArgumentCaptor<Predicate<BuildScanPublishing.PublishingContext>> specCaptor = withCapturedPublishingSpec(api);

        // when
        adapter.publishOnDemand();

        // then
        assertFalse(specCaptor.getValue().test(publishingCtxForSuccessfulBuild()));
        assertFalse(specCaptor.getValue().test(publishingCtxForFailingBuild()));
    }

    @Test
    @DisplayName("can configure the data obfuscation using an action")
    void testObfuscationAction() {
        // given
        BuildScanDataObfuscation obfuscation = mock();
        when(api.getObfuscation()).thenReturn(obfuscation);

        // and
        adapter = new DevelocityBuildScanApiAdapter(api);

        // when
        adapter.obfuscation(o -> {
            o.hostname(it -> "<obfuscated>");
            o.username(it -> "<obfuscated>");
        });

        // then
        verify(obfuscation).hostname(any());
        verify(obfuscation).username(any());
    }

    @Test
    @DisplayName("can configure the data capturing using an action")
    void testCaptureAction() {
        // given
        BuildScanCaptureSettings capture = mock();
        when(api.getCapture()).thenReturn(capture);

        // and
        adapter = new DevelocityBuildScanApiAdapter(api);

        // when
        adapter.capture(o -> {
            o.setGoalInputFiles(true);
            o.setTestLogging(false);
        });

        // then
        verify(capture).setFileFingerprints(true);
        verify(capture).setTestLogging(false);
    }

    @Test
    @DisplayName("can set and retrieve the uploadInBackground value using adapter")
    void testUploadInBackground() {
        // when
        adapter.setUploadInBackground(true);

        // then
        verify(api).setUploadInBackground(true);

        // when
        when(api.isUploadInBackground()).thenReturn(true);

        // then
        assertTrue(adapter.isUploadInBackground());
    }

    @Test
    @DisplayName("executeOnce action can be configured via an adapter")
    void testExecuteOnceAction() {
        //given
        doExecuteActionWith(api, 1).when(api).executeOnce(any(), any());

        // when
        adapter.executeOnce("id", b -> {
            b.setTermsOfUseUrl("other url");
            b.setTermsOfUseAgree("no");
        });

        // then
        verify(api).setTermsOfUseUrl("other url");
        verify(api).setTermsOfUseAgree("no");
    }

    @SuppressWarnings("unchecked")
    private ArgumentCaptor<Predicate<BuildScanPublishing.PublishingContext>> withCapturedPublishingSpec(BuildScanApi api) {
        ArgumentCaptor<Predicate<BuildScanPublishing.PublishingContext>> specCaptor = ArgumentCaptor.forClass(Predicate.class);
        BuildScanPublishing publishing = mock();
        doAnswer(it -> publishing).when(publishing).onlyIf(specCaptor.capture());
        doExecuteActionWith(publishing).when(api).publishing(any());
        return specCaptor;
    }

    private BuildScanPublishing.PublishingContext publishingCtxForSuccessfulBuild() {
        BuildScanPublishing.PublishingContext ctx = mock();
        lenient().when(ctx.getBuildResult()).thenReturn(mock(BuildResult.class));
        lenient().when(ctx.getBuildResult().getFailures()).thenReturn(Collections.emptyList());
        return ctx;
    }

    private BuildScanPublishing.PublishingContext publishingCtxForFailingBuild() {
        BuildScanPublishing.PublishingContext ctx = mock();
        lenient().when(ctx.getBuildResult()).thenReturn(mock(BuildResult.class));
        lenient().when(ctx.getBuildResult().getFailures()).thenReturn(Collections.singletonList(new RuntimeException("Boom!")));
        return ctx;
    }

}
