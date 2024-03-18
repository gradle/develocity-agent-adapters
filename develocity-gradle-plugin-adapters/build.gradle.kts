import develocity.adapters.sourceSetOutput

plugins {
    id("develocity.adapters-library")
}

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

    "develocityCompatibilityCompileOnly"(gradleApi())
    "develocityCompatibilityCompileOnly"(libs.develocity.plugin)
    "develocityCompatibilityImplementation"(sourceSetOutput("compatibilityApi"))
}
