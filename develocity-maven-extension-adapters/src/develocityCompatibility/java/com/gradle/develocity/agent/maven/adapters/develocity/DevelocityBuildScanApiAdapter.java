package com.gradle.develocity.agent.maven.adapters.develocity;

import com.gradle.develocity.agent.maven.adapters.BuildResultAdapter;
import com.gradle.develocity.agent.maven.adapters.BuildScanApiAdapter;
import com.gradle.develocity.agent.maven.adapters.BuildScanCaptureAdapter;
import com.gradle.develocity.agent.maven.adapters.BuildScanDataObfuscationAdapter;
import com.gradle.develocity.agent.maven.adapters.Property;
import com.gradle.develocity.agent.maven.adapters.PublishedBuildScanAdapter;
import com.gradle.develocity.agent.maven.adapters.shared.DefaultBuildResultAdapter;
import com.gradle.develocity.agent.maven.adapters.shared.DefaultBuildScanCaptureAdapter;
import com.gradle.develocity.agent.maven.adapters.shared.DefaultBuildScanDataObfuscationAdapter;
import com.gradle.develocity.agent.maven.adapters.shared.DefaultPublishedBuildScanAdapter;
import com.gradle.develocity.agent.maven.api.scan.BuildScanApi;

import java.net.URI;
import java.util.function.Consumer;

class DevelocityBuildScanApiAdapter implements BuildScanApiAdapter {

    private final BuildScanApi buildScan;
    private final BuildScanDataObfuscationAdapter obfuscation;
    private final BuildScanCaptureAdapter capture;

    DevelocityBuildScanApiAdapter(BuildScanApi buildScan) {
        this.buildScan = buildScan;
        this.obfuscation = new DefaultBuildScanDataObfuscationAdapter(
            buildScan.getObfuscation()::username,
            buildScan.getObfuscation()::hostname,
            buildScan.getObfuscation()::ipAddresses
        );
        this.capture = new DefaultBuildScanCaptureAdapter(
            Property.create(buildScan.getCapture()::setFileFingerprints, buildScan.getCapture()::isFileFingerprints),
            Property.create(buildScan.getCapture()::setBuildLogging, buildScan.getCapture()::isBuildLogging),
            Property.create(buildScan.getCapture()::setTestLogging, buildScan.getCapture()::isTestLogging)
        );
    }

    @Override
    public void tag(String tag) {
        buildScan.tag(tag);
    }

    @Override
    public void value(String name, String value) {
        buildScan.value(name, value);
    }

    @Override
    public void link(String name, String url) {
        buildScan.link(name, url);
    }

    @Override
    public void background(Consumer<? super BuildScanApiAdapter> action) {
        buildScan.background(__ -> action.accept(this));
    }

    @Override
    public void buildFinished(Consumer<? super BuildResultAdapter> action) {
        buildScan.buildFinished(result -> action.accept(new DefaultBuildResultAdapter(result.getFailures())));
    }

    @Override
    public void buildScanPublished(Consumer<? super PublishedBuildScanAdapter> action) {
        buildScan.buildScanPublished(scan -> action.accept(new DefaultPublishedBuildScanAdapter(scan.getBuildScanId(), scan.getBuildScanUri())));
    }

    @Override
    public void setTermsOfUseUrl(String termsOfServiceUrl) {
        buildScan.setTermsOfUseUrl(termsOfServiceUrl);
    }

    @Override
    public String getTermsOfUseUrl() {
        return buildScan.getTermsOfUseUrl();
    }

    @Override
    public void setTermsOfUseAgree(String agree) {
        buildScan.setTermsOfUseAgree(agree);
    }

    @Override
    public String getTermsOfUseAgree() {
        return buildScan.getTermsOfUseAgree();
    }

    @Override
    public void setServer(URI url) {
        buildScan.setServer(url);
    }

    @Override
    public String getServer() {
        return buildScan.getServer();
    }

    @Override
    public void setAllowUntrustedServer(boolean allow) {
        buildScan.setAllowUntrustedServer(allow);
    }

    @Override
    public boolean getAllowUntrustedServer() {
        return buildScan.getAllowUntrustedServer();
    }

    @Override
    public void publishAlways() {
        buildScan.publishing(p -> p.onlyIf(ctx -> true));
    }

    @Override
    public void publishAlwaysIf(boolean condition) {
        buildScan.publishing(p -> p.onlyIf(ctx -> condition));
    }

    @Override
    public void publishOnFailure() {
        buildScan.publishing(p -> p.onlyIf(ctx -> !ctx.getBuildResult().getFailures().isEmpty()));
    }

    @Override
    public void publishOnFailureIf(boolean condition) {
        buildScan.publishing(p -> p.onlyIf(ctx -> !ctx.getBuildResult().getFailures().isEmpty() && condition));
    }

    @Override
    public void publishOnDemand() {
        // on-demand publication in the new API is controlled only via -Dscan property
        buildScan.publishing(p -> p.onlyIf(ctx -> false));
    }

    @Override
    public void setUploadInBackground(boolean uploadInBackground) {
        buildScan.setUploadInBackground(uploadInBackground);
    }

    @Override
    public boolean isUploadInBackground() {
        return buildScan.isUploadInBackground();
    }

    @Override
    public void executeOnce(String identifier, Consumer<? super BuildScanApiAdapter> action) {
        buildScan.executeOnce(identifier, __ -> action.accept(this));
    }

    @Override
    public BuildScanDataObfuscationAdapter getObfuscation() {
        return obfuscation;
    }

    @Override
    public BuildScanCaptureAdapter getCapture() {
        return capture;
    }

}
