package com.gradle.develocity.agent.gradle.adapters.develocity;

import com.gradle.develocity.agent.gradle.adapters.BuildCacheConfigurationAdapter;

import org.gradle.caching.configuration.BuildCache;
import org.gradle.caching.configuration.BuildCacheConfiguration;
import org.gradle.caching.http.HttpBuildCache;
import org.gradle.caching.local.DirectoryBuildCache;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;

/**
 * Provides a common adapter layer for any `BuildCacheConfiguration` instance supplied by Gradle.
 * Several different remote build cache implementations are adapted:
 * - org.gradle.caching.http.HttpBuildCache
 * - com.gradle.develocity.agent.gradle.buildcache.DevelocityBuildCache
 * - com.gradle.enterprise.gradleplugin.GradleEnterpriseBuildCache
 */
public class GradleBuildCacheConfigurationAdapter implements BuildCacheConfigurationAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(GradleBuildCacheConfigurationAdapter.class);

    private final BuildCacheConfiguration buildCache;

    public GradleBuildCacheConfigurationAdapter(BuildCacheConfiguration buildCache) {
        this.buildCache = buildCache;
    }

    @Override
    public LocalBuildCacheAdapter getLocal() {
        return new LocalBuildCache(buildCache.getLocal());
    }

    @Override
    public @Nullable RemoteBuildCacheAdapter getRemote() {
        BuildCache remoteConfig = buildCache.getRemote();
        if (remoteConfig == null) {
            return null;
        } else if (remoteConfig instanceof HttpBuildCache) {
            return new HttpRemoteBuildCache((HttpBuildCache) remoteConfig);
        } else {
            return new ReflectingRemoteBuildCache(remoteConfig);
        }
    }

    private static class LocalBuildCache implements LocalBuildCacheAdapter {
        private final DirectoryBuildCache localBuildCache;

        private LocalBuildCache(DirectoryBuildCache localBuildCache) {
            this.localBuildCache = localBuildCache;
        }

        @Override
        public boolean isEnabled() {
            return localBuildCache.isEnabled();
        }

        @Override
        public void setEnabled(boolean enabled) {
            localBuildCache.setEnabled(enabled);
        }

        @Override
        public boolean isPush() {
            return localBuildCache.isPush();
        }

        @Override
        public void setPush(boolean push) {
            localBuildCache.setPush(push);
        }

        @Override
        public String getDirectory() {
            return String.valueOf(localBuildCache.getDirectory());
        }

        @Override
        public void setDirectory(String directory) {
            localBuildCache.setDirectory(directory);
        }

        @Override
        public int getRemoveUnusedEntriesAfterDays() {
            return localBuildCache.getRemoveUnusedEntriesAfterDays();
        }

        @Override
        public void setRemoveUnusedEntriesAfterDays(int days) {
            localBuildCache.setRemoveUnusedEntriesAfterDays(days);
        }
    }

    private static class HttpRemoteBuildCache implements RemoteBuildCacheAdapter {
        private final HttpBuildCache remoteBuildCache;

        private HttpRemoteBuildCache(HttpBuildCache remoteBuildCache) {
            this.remoteBuildCache = remoteBuildCache;
        }

        @Override
        public boolean isEnabled() {
            return remoteBuildCache.isEnabled();
        }

        @Override
        public void setEnabled(boolean enabled) {
            remoteBuildCache.setEnabled(enabled);
        }

        @Override
        public boolean isPush() {
            return remoteBuildCache.isPush();
        }

        @Override
        public void setPush(boolean push) {
            remoteBuildCache.setPush(push);
        }

        @Override
        public @Nullable String getUrl() {
            URI url = remoteBuildCache.getUrl();
            return url == null ? null : url.toASCIIString();
        }

        @Override
        public void setUrl(String url) {
            remoteBuildCache.setUrl(url);
        }

        @Override
        public @Nullable String getServer() {
            warnAboutUnsupportedMethod("getServer");
            return null;
        }

        @Override
        public void setServer(@Nullable String server) {
            warnAboutUnsupportedMethod("setServer");
        }

        @Override
        public @Nullable String getPath() {
            warnAboutUnsupportedMethod("getPath");
            return null;
        }

        @Override
        public void setPath(@Nullable String path) {
            warnAboutUnsupportedMethod("setPath");
        }

        @Override
        public boolean getAllowUntrustedServer() {
            return remoteBuildCache.isAllowUntrustedServer();
        }

        @Override
        public void setAllowUntrustedServer(boolean allowUntrusted) {
            remoteBuildCache.setAllowUntrustedServer(allowUntrusted);
        }

        @Override
        public boolean getAllowInsecureProtocol() {
            return remoteBuildCache.isAllowInsecureProtocol();
        }

        @Override
        public void setAllowInsecureProtocol(boolean allowInsecureProtocol) {
            remoteBuildCache.setAllowInsecureProtocol(allowInsecureProtocol);
        }

        @Override
        public boolean getUseExpectContinue() {
            warnAboutUnsupportedMethod("getUseExpectContinue");
            return false;
        }

        @Override
        public void setUseExpectContinue(boolean useExpectContinue) {
            warnAboutUnsupportedMethod("setUseExpectContinue");
        }

        @Override
        public @Nullable Object getUsernameAndPassword() {
            warnAboutUnsupportedMethod("getUsernameAndPassword");
            return null;
        }

        @Override
        public void usernameAndPassword(String username, String password) {
            warnAboutUnsupportedMethod("usernameAndPassword");
        }
    }

    private static class ReflectingRemoteBuildCache implements RemoteBuildCacheAdapter {
        private final BuildCache remoteBuildCache;

        private ReflectingRemoteBuildCache(BuildCache remoteBuildCache) {
            this.remoteBuildCache = remoteBuildCache;
        }

        @Override
        public boolean isEnabled() {
            return remoteBuildCache.isEnabled();
        }

        @Override
        public void setEnabled(boolean enabled) {
            remoteBuildCache.setEnabled(enabled);
        }

        @Override
        public boolean isPush() {
            return remoteBuildCache.isPush();
        }

        @Override
        public void setPush(boolean push) {
            remoteBuildCache.setPush(push);
        }

        @Override
        public @Nullable String getUrl() {
            warnAboutUnsupportedMethod("getUrl");
            return null;
        }

        @Override
        public void setUrl(@Nullable String url) {
            warnAboutUnsupportedMethod("setUrl");
        }

        @Override
        public @Nullable String getServer() {
            return getStringProperty("getServer");
        }

        @Override
        public void setServer(@Nullable String server) {
            setStringProperty("setServer", server);
        }

        @Override
        public @Nullable String getPath() {
            return getStringProperty("getPath");
        }

        @Override
        public void setPath(@Nullable String path) {
            setStringProperty("setPath", path);
        }

        @Override
        public boolean getAllowUntrustedServer() {
            return getBooleanProperty("getAllowUntrustedServer");
        }

        @Override
        public void setAllowUntrustedServer(boolean allowUntrusted) {
            setBooleanProperty("setAllowUntrustedServer", allowUntrusted);
        }

        @Override
        public boolean getAllowInsecureProtocol() {
            return getBooleanProperty("getAllowInsecureProtocol");
        }

        @Override
        public void setAllowInsecureProtocol(boolean allowInsecureProtocol) {
            setBooleanProperty("setAllowInsecureProtocol", allowInsecureProtocol);
        }

        @Override
        public boolean getUseExpectContinue() {
            return getBooleanProperty("getUseExpectContinue");
        }

        @Override
        public void setUseExpectContinue(boolean useExpectContinue) {
            setBooleanProperty("setUseExpectContinue", useExpectContinue);
        }

        @Override
        public @Nullable Object getUsernameAndPassword() {
            return getProperty("getUsernameAndPassword");
        }

        @Override
        public void usernameAndPassword(String username, String password) {
            try {
                Method setter = remoteBuildCache.getClass().getDeclaredMethod("usernameAndPassword", String.class, String.class);
                setter.invoke(remoteBuildCache, username, password);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                warnAboutUnsupportedMethod("usernameAndPassword");
            }
        }

        private Object getProperty(String name) {
            try {
                Method getter = remoteBuildCache.getClass().getDeclaredMethod(name);
                return getter.invoke(remoteBuildCache);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                warnAboutUnsupportedMethod(name);
                return null;
            }
        }

        private <T> void setProperty(String name, Class<T> type, T value) {
            try {
                Method setter = remoteBuildCache.getClass().getDeclaredMethod(name, type);
                setter.invoke(remoteBuildCache, value);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                warnAboutUnsupportedMethod(name);
            }
        }

        private String getStringProperty(String name) {
            return (String) getProperty(name);
        }

        private void setStringProperty(String name, String value) {
            setProperty(name, String.class, value);
        }

        private boolean getBooleanProperty(String name) {
            return Boolean.TRUE.equals(getProperty(name));
        }

        private void setBooleanProperty(String name, boolean value) {
            setProperty(name, Boolean.TYPE, value);
        }
    }

    private static void warnAboutUnsupportedMethod(String name) {
        LOG.warn("Remote Build Cache instance does not support the {} method", name);
    }
}
