pluginManagement {
    plugins {
        id("com.gradle.enterprise") version "3.16.2"
        id("com.gradle.enterprise.gradle-enterprise-conventions-plugin") version "0.7.5"
    }
}

plugins {
    id("com.gradle.enterprise")
    id("com.gradle.enterprise.gradle-enterprise-conventions-plugin")
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "develocity-agent-adapters"
include("develocity-gradle-plugin-adapters")
