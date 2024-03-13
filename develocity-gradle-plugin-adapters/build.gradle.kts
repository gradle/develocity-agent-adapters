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

dependencies {
    "compatibilityApiCompileOnly"(gradleApi())

    "enterpriseCompatibilityCompileOnly"(gradleApi())
    "enterpriseCompatibilityCompileOnly"("com.gradle:gradle-enterprise-gradle-plugin:3.16.2")
    "enterpriseCompatibilityImplementation"(sourceSets["compatibilityApi"].output)

    "develocityCompatibilityCompileOnly"(gradleApi())
    "develocityCompatibilityCompileOnly"("com.gradle:develocity-gradle-plugin:3.17-rc-4")
    "develocityCompatibilityImplementation"(sourceSets["compatibilityApi"].output)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

tasks.jar {
    from(sourceSets["compatibilityApi"].output)
    from(sourceSets["enterpriseCompatibility"].output)
    from(sourceSets["develocityCompatibility"].output)
}

