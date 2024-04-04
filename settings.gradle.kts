plugins {
    id("com.gradle.enterprise") version "3.16.2"
    id("com.gradle.common-custom-user-data-gradle-plugin") version "1.13"
}

rootProject.name = "develocity-agent-adapters"
include("develocity-gradle-plugin-adapters")
include("develocity-maven-extension-adapters")
