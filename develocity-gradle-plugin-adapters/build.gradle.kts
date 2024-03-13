plugins {
    java
    `java-test-fixtures`
}

repositories {
    gradlePluginPortal()
}

sourceSets {
    create("compatibilityApi")
    create("enterpriseCompatibility")
}

dependencies {
    "compatibilityApiCompileOnly"(gradleApi())

    "enterpriseCompatibilityCompileOnly"(gradleApi())
    "enterpriseCompatibilityCompileOnly"("com.gradle:gradle-enterprise-gradle-plugin:3.16.2")
    "enterpriseCompatibilityImplementation"(sourceSets["compatibilityApi"].output)

    testFixturesImplementation(gradleApi())
    testFixturesImplementation("org.mockito:mockito-core:4.11.0")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

tasks.jar {
    from(sourceSets["compatibilityApi"].output)
    from(sourceSets["enterpriseCompatibility"].output)
}

testing {
    suites {
        create<JvmTestSuite>("enterpriseCompatibilityTest") {
            useJUnitJupiter()

            dependencies {
                implementation(gradleApi())
                implementation(sourceSets["compatibilityApi"].output)
                implementation(sourceSets["enterpriseCompatibility"].output)
                implementation(testFixtures(project()))
                implementation(platform("org.junit:junit-bom:5.10.2"))
                implementation("org.junit.jupiter:junit-jupiter")
                implementation("org.mockito:mockito-core:4.11.0")
                implementation("org.mockito:mockito-junit-jupiter:4.11.0")
                implementation("com.gradle:gradle-enterprise-gradle-plugin:3.16.2") {
                    because("Verify that reflective proxies and adapters work with actual plugin classes")
                }
            }
        }
    }
}

tasks.named("check") {
    dependsOn(testing.suites["enterpriseCompatibilityTest"])
}
