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

dependencies {
    "compatibilityApiCompileOnly"("org.apache.maven:maven-core:3.9.6")

    "enterpriseCompatibilityCompileOnly"("org.apache.maven:maven-core:3.9.6")
    "enterpriseCompatibilityCompileOnly"("com.gradle:gradle-enterprise-maven-extension:1.20.1")
    "enterpriseCompatibilityImplementation"(sourceSets["compatibilityApi"].output)

    "develocityCompatibilityCompileOnly"("org.apache.maven:maven-core:3.9.6")
    "develocityCompatibilityCompileOnly"("com.gradle:develocity-maven-extension:1.21-rc-5")
    "develocityCompatibilityImplementation"(sourceSets["compatibilityApi"].output)
}

tasks.jar {
    from(sourceSets["compatibilityApi"].output)
    from(sourceSets["enterpriseCompatibility"].output)
    from(sourceSets["develocityCompatibility"].output)
}
