plugins {
    java
}

sourceSets {
    create("compatibilityApi")
}

dependencies {
    "compatibilityApiCompileOnly"("org.apache.maven:maven-core:3.9.6")
}

tasks.jar {
    from(sourceSets["compatibilityApi"].output)
}
