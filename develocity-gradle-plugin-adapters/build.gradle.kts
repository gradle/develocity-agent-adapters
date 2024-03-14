import develocity.adapters.sourceSetOutput

plugins {
    id("develocity.adapters-library")
}

version = "1.0"

repositories {
    gradlePluginPortal()
    maven {
        url = uri("https://repo.grdev.net/artifactory/public")
        content {
            includeVersion("com.gradle", "develocity-gradle-plugin", "3.17-rc-4")
        }
    }
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

    testFixturesImplementation(gradleApi())
}

develocityAdaptersPublication {
    name = "Develocity Gradle API adapters for Develocity and Gradle Enterprise Gradle plugins"
    description = "Adapter interfaces for interacting with the Develocity and Gradle Enterprise Gradle plugins"
}
