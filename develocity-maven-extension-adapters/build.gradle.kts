import develocity.adapters.sourceSetOutput

plugins {
    id("develocity.adapters-library")
}

version = "1.0"

repositories {
    maven {
        url = uri("https://repo.grdev.net/artifactory/public")
        content {
            includeVersion("com.gradle", "develocity-maven-extension", "1.21-rc-5")
        }
    }
}

dependencies {
    "compatibilityApiCompileOnly"(libs.maven.core)

    "enterpriseCompatibilityCompileOnly"(libs.maven.core)
    "enterpriseCompatibilityCompileOnly"(libs.gradle.enterprise.extension)
    "enterpriseCompatibilityImplementation"(sourceSetOutput("compatibilityApi"))

    "develocityCompatibilityCompileOnly"(libs.maven.core)
    "develocityCompatibilityCompileOnly"(libs.develocity.extension)
    "develocityCompatibilityImplementation"(sourceSetOutput("compatibilityApi"))
}

develocityAdaptersPublication {
    name = "Develocity API adapters for Develocity and Gradle Enterprise Maven extensions"
    description = "Adapter interfaces for interacting with the Develocity and Gradle Enterprise Maven extension APIs"
}
