plugins {
    id("com.gradle.develocity") version "3.17.2"
    id("com.gradle.enterprise.gradle-enterprise-conventions-plugin") version "0.7.5"
}

rootProject.name = "develocity-agent-adapters"
include("develocity-gradle-plugin-adapters")
include("develocity-maven-extension-adapters")
