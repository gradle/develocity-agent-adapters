plugins {
    id("com.gradle.develocity") version "3.17"
    id("com.gradle.common-custom-user-data-gradle-plugin") version "1.13"
}

rootProject.name = "develocity-agent-adapters"
include("develocity-gradle-plugin-adapters")
include("develocity-maven-extension-adapters")

develocity {
    buildScan {
        publishing.onlyIf { false }
    }
}
