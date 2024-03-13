plugins {
    java
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.grdev.net/artifactory/public")
        content {
            includeVersion("com.gradle", "develocity-maven-extension", "1.21-rc-5")
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
    "compatibilityApiCompileOnly"(libs.maven.core)

    "enterpriseCompatibilityCompileOnly"(libs.maven.core)
    "enterpriseCompatibilityCompileOnly"(libs.gradle.enterprise.extension)
    "enterpriseCompatibilityImplementation"(sourceSet("compatibilityApi"))

    "develocityCompatibilityCompileOnly"(libs.maven.core)
    "develocityCompatibilityCompileOnly"(libs.develocity.extension)
    "develocityCompatibilityImplementation"(sourceSet("compatibilityApi"))
}

tasks.jar {
    from(sourceSet("compatibilityApi"))
    from(sourceSet("enterpriseCompatibility"))
    from(sourceSet("develocityCompatibility"))
}
