plugins {
    java
}

sourceSets {
    create("compatibilityApi")
    create("enterpriseCompatibility")
}

dependencies {
    "compatibilityApiCompileOnly"("org.apache.maven:maven-core:3.9.6")

    "enterpriseCompatibilityCompileOnly"("org.apache.maven:maven-core:3.9.6")
    "enterpriseCompatibilityCompileOnly"("com.gradle:gradle-enterprise-maven-extension:1.20.1")
    "enterpriseCompatibilityImplementation"(sourceSets["compatibilityApi"].output)
}

tasks.jar {
    from(sourceSets["compatibilityApi"].output)
    from(sourceSets["enterpriseCompatibility"].output)
}
