package com.gradle.develocity.agent.maven.adapters.develocity;

import com.gradle.develocity.agent.maven.adapters.BuildCacheApiAdapter;
import com.gradle.develocity.agent.maven.adapters.LocalBuildCacheAdapter;
import com.gradle.develocity.agent.maven.adapters.MojoMetadataProviderAdapter;
import com.gradle.develocity.agent.maven.adapters.NormalizationProviderAdapter;
import com.gradle.develocity.agent.maven.adapters.Property;
import com.gradle.develocity.agent.maven.adapters.RemoteBuildCacheAdapter;
import com.gradle.develocity.agent.maven.adapters.shared.DefaultCleanupPolicyAdapter;
import com.gradle.develocity.agent.maven.adapters.shared.DefaultCredentialsAdapter;
import com.gradle.develocity.agent.maven.adapters.shared.DefaultLocalBuildCacheAdapter;
import com.gradle.develocity.agent.maven.adapters.shared.DefaultRemoteBuildCacheAdapter;
import com.gradle.develocity.agent.maven.adapters.shared.DefaultServerAdapter;
import com.gradle.develocity.agent.maven.api.cache.BuildCacheApi;
import com.gradle.develocity.agent.maven.api.cache.CleanupPolicy;
import com.gradle.develocity.agent.maven.api.cache.Credentials;
import com.gradle.develocity.agent.maven.api.cache.LocalBuildCache;
import com.gradle.develocity.agent.maven.api.cache.RemoteBuildCache;
import com.gradle.develocity.agent.maven.api.cache.Server;

class DevelocityBuildCacheApiAdapter implements BuildCacheApiAdapter {

    private final BuildCacheApi buildCache;
    private final LocalBuildCacheAdapter localCache;
    private final RemoteBuildCacheAdapter remoteCache;

    DevelocityBuildCacheApiAdapter(BuildCacheApi buildCache) {
        this.buildCache = buildCache;
        this.localCache = createLocalCacheAdapter(buildCache);
        this.remoteCache = createRemoteCacheAdapter(buildCache);
    }

    private static DefaultLocalBuildCacheAdapter createLocalCacheAdapter(BuildCacheApi buildCache) {
        LocalBuildCache local = buildCache.getLocal();
        CleanupPolicy cleanupPolicy = local.getCleanupPolicy();
        return new DefaultLocalBuildCacheAdapter(
            Property.create(local::setEnabled, local::isEnabled),
            Property.create(local::setStoreEnabled, local::isStoreEnabled),
            Property.create(local::setDirectory, local::getDirectory),
            new DefaultCleanupPolicyAdapter(
                Property.create(cleanupPolicy::setEnabled, cleanupPolicy::isEnabled),
                Property.create(cleanupPolicy::setRetentionPeriod, cleanupPolicy::getRetentionPeriod),
                Property.create(cleanupPolicy::setCleanupInterval, cleanupPolicy::getCleanupInterval)
            )
        );
    }

    private static DefaultRemoteBuildCacheAdapter createRemoteCacheAdapter(BuildCacheApi buildCache) {
        RemoteBuildCache remote = buildCache.getRemote();
        Server server = remote.getServer();
        return new DefaultRemoteBuildCacheAdapter(
            Property.create(remote::setEnabled, remote::isEnabled),
            Property.create(remote::setStoreEnabled, remote::isStoreEnabled),
            createServerAdapter(server)
        );
    }

    private static DefaultServerAdapter createServerAdapter(Server server) {
        Credentials credentials = server.getCredentials();
        return new DefaultServerAdapter(
            Property.create(server::setServerId, server::getServerId),
            Property.create(server::setUrl, server::getUrl),
            Property.create(server::setAllowUntrusted, server::isAllowUntrusted),
            Property.create(server::setAllowInsecureProtocol, server::isAllowInsecureProtocol),
            Property.create(server::setUseExpectContinue, server::isUseExpectContinue),
            new DefaultCredentialsAdapter(
                Property.create(credentials::setUsername, credentials::getUsername),
                Property.create(credentials::setPassword, credentials::getPassword)
            )
        );
    }

    @Override
    public LocalBuildCacheAdapter getLocal() {
        return localCache;
    }

    @Override
    public RemoteBuildCacheAdapter getRemote() {
        return remoteCache;
    }

    @Override
    public boolean isRequireClean() {
        return buildCache.isRequireClean();
    }

    @Override
    public void setRequireClean(boolean requireClean) {
        buildCache.setRequireClean(requireClean);
    }

    @Override
    public void registerMojoMetadataProvider(MojoMetadataProviderAdapter metadataProvider) {
        buildCache.registerMojoMetadataProvider(ctx -> metadataProvider.provideMetadata(new DevelocityMojoMetadataContext(ctx)));
    }

    @Override
    public void registerNormalizationProvider(NormalizationProviderAdapter normalizationProvider) {
        buildCache.registerNormalizationProvider(ctx -> normalizationProvider.configureNormalization(new DevelocityNormalizationContext(ctx)));
    }
}
