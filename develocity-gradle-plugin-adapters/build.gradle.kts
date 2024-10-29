import develocity.adapters.sourceSetOutput

plugins {
    id("develocity.adapters-library")
}

version = "1.0.5"

repositories {
    gradlePluginPortal()
}

dependencies {
    "compatibilityApiCompileOnly"(gradleApi())

    "enterpriseCompatibilityCompileOnly"(gradleApi())
    "enterpriseCompatibilityCompileOnly"(libs.gradle.enterprise.plugin)
    "enterpriseCompatibilityImplementation"(sourceSetOutput("compatibilityApi"))
    "enterpriseCompatibilityTestImplementation"(gradleApi())
    "enterpriseCompatibilityTestImplementation"(libs.gradle.enterprise.plugin)

    "develocityCompatibilityCompileOnly"(gradleApi())
    "develocityCompatibilityCompileOnly"(libs.develocity.plugin)
    "develocityCompatibilityImplementation"(sourceSetOutput("compatibilityApi"))
    "develocityCompatibilityTestImplementation"(gradleApi())
    "develocityCompatibilityTestImplementation"(libs.develocity.plugin)

    testFixturesImplementation(gradleApi())
}

develocityAdaptersPublication {
    name = "Develocity Gradle API adapters for Develocity and Gradle Enterprise Gradle plugins"
    description = "Adapter interfaces for interacting with the Develocity and Gradle Enterprise Gradle plugins"
}
