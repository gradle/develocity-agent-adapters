import develocity.adapters.sourceSetOutput

plugins {
    java
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

sourceSets {
    create("compatibilityApi")
    create("enterpriseCompatibility")
    create("develocityCompatibility")
}

tasks.jar {
    from(sourceSetOutput("compatibilityApi"))
    from(sourceSetOutput("enterpriseCompatibility"))
    from(sourceSetOutput("develocityCompatibility"))
}
