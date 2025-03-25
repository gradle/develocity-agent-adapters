import develocity.adapters.sourceSetOutput

plugins {
    id("develocity.adapters-library")
}

version = "1.1.1"

repositories {
    gradlePluginPortal()
}

dependencies {
    "compatibilityApiCompileOnly"(gradleApi())

    "enterpriseCompatibilityCompileOnly"(gradleApi())
    "enterpriseCompatibilityImplementation"(sourceSetOutput("compatibilityApi"))
    "enterpriseCompatibilityTestImplementation"(gradleApi())
    "enterpriseCompatibilityTestImplementation"(libs.gradle.enterprise.plugin)

    "develocityCompatibilityCompileOnly"(gradleApi())
    "develocityCompatibilityImplementation"(sourceSetOutput("compatibilityApi"))
    "develocityCompatibilityTestImplementation"(gradleApi())
    "develocityCompatibilityTestImplementation"(libs.develocity.plugin)

    testFixturesImplementation(gradleApi())
}

develocityAdaptersPublication {
    name = "Develocity Gradle API adapters for Develocity and Gradle Enterprise Gradle plugins"
    description = "Adapter interfaces for interacting with the Develocity and Gradle Enterprise Gradle plugins"
}
