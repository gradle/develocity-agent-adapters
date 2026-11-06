import develocity.adapters.sourceSetOutput

plugins {
    id("develocity.adapters-library")
}

version = "1.0.1"

repositories {
    mavenCentral()
}

dependencies {
    "compatibilityApiCompileOnly"(libs.maven.core)

    "enterpriseCompatibilityCompileOnly"(libs.maven.core)
    "enterpriseCompatibilityCompileOnly"(libs.gradle.enterprise.extension)
    "enterpriseCompatibilityImplementation"(sourceSetOutput("compatibilityApi"))
    "enterpriseCompatibilityTestImplementation"(libs.maven.core)
    "enterpriseCompatibilityTestImplementation"(libs.gradle.enterprise.extension)
    "enterpriseCompatibilityTestRuntimeOnly"(libs.log4j)

    "develocityCompatibilityCompileOnly"(libs.maven.core)
    "develocityCompatibilityCompileOnly"(libs.develocity.extension)
    "develocityCompatibilityImplementation"(sourceSetOutput("compatibilityApi"))
    "develocityCompatibilityTestImplementation"(libs.maven.core)
    "develocityCompatibilityTestImplementation"(libs.develocity.extension)
    "develocityCompatibilityTestRuntimeOnly"(libs.log4j)

    constraints {
        // maven-core 3.10.0 brings in Guava 33.7.1, which is affected by GHSA-xxph-c9ww-hj94.
        // Remove once a maven-core release depends on Guava 33.7.2 or later.
        listOf(
            "compatibilityApiCompileOnly",
            "enterpriseCompatibilityCompileOnly",
            "enterpriseCompatibilityTestImplementation",
            "develocityCompatibilityCompileOnly",
            "develocityCompatibilityTestImplementation",
        ).forEach { add(it, libs.guava) }
    }
}

develocityAdaptersPublication {
    name = "Develocity API adapters for Develocity and Gradle Enterprise Maven extensions"
    description = "Adapter interfaces for interacting with the Develocity and Gradle Enterprise Maven extension APIs"
}
