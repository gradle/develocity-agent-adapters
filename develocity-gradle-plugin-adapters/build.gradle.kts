plugins {
    java
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

sourceSets {
    create("compatibilityApi")
    create("enterpriseCompatibility")
    create("develocityCompatibility")
}

fun sourceSet(name: String): SourceSetOutput = sourceSets[name].output

dependencies {
    "compatibilityApiCompileOnly"(gradleApi())

    "enterpriseCompatibilityCompileOnly"(gradleApi())
    "enterpriseCompatibilityCompileOnly"(libs.gradle.enterprise.plugin)
    "enterpriseCompatibilityImplementation"(sourceSet("compatibilityApi"))

    "develocityCompatibilityCompileOnly"(gradleApi())
    "develocityCompatibilityCompileOnly"(libs.develocity.plugin)
    "develocityCompatibilityImplementation"(sourceSet("compatibilityApi"))
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

tasks.jar {
    from(sourceSet("compatibilityApi"))
    from(sourceSet("enterpriseCompatibility"))
    from(sourceSet("develocityCompatibility"))
}

