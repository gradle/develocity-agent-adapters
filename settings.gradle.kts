plugins {
    id("com.gradle.develocity") version "4.1"
    id("com.gradle.common-custom-user-data-gradle-plugin") version "2.3"
}

val isCI = providers.environmentVariable("CI").presence()

develocity {
    server = "https://ge.gradle.org"
    buildScan {
        uploadInBackground = isCI.not()
        publishing.onlyIf { it.isAuthenticated }
        obfuscation {
            ipAddresses { addresses -> addresses.map { "0.0.0.0" } }
        }
    }
}

buildCache {
    local {
        isEnabled = true
    }

    remote(develocity.buildCache) {
        server = "https://eu-build-cache.gradle.org"
        isEnabled = true
        val hasAccessKey = providers.environmentVariable("DEVELOCITY_ACCESS_KEY").map { it.isNotBlank() }.orElse(false)
        isPush = hasAccessKey.zip(isCI) { accessKey, ci -> ci && accessKey }.get()
    }
}

rootProject.name = "develocity-agent-adapters"

include("develocity-gradle-plugin-adapters")
include("develocity-maven-extension-adapters")

fun Provider<*>.presence(): Provider<Boolean> = map { true }.orElse(false)
fun Provider<Boolean>.not(): Provider<Boolean> = map { !it }
