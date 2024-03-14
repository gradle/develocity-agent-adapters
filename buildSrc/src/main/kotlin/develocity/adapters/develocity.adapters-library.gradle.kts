import develocity.adapters.AdaptersPublicationExtension
import develocity.adapters.sourceSetOutput
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    java
    `java-test-fixtures`
    `jvm-test-suite`
    `maven-publish`
    signing
}

val publicationExtension = project.extensions.create<AdaptersPublicationExtension>("develocityAdaptersPublication")

repositories {
    mavenCentral()
}

base {
    archivesName = project.name
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
    withJavadocJar()
    withSourcesJar()
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

val libs = the<LibrariesForLibs>()
dependencies {
    testFixturesImplementation(libs.mockito.core)
}

testing {
    suites {
        val enterpriseCompatibilityTest by creating(JvmTestSuite::class) {
            useJUnitJupiter()

            dependencies {
                implementation(sourceSetOutput("enterpriseCompatibility"))
                implementation(testFixtures(project()))
                implementation(platform(libs.junit.bom))
                implementation("org.junit.jupiter:junit-jupiter")
                implementation(libs.mockito.core)
                implementation(libs.mockito.jupiter)
            }
        }

        val develocityCompatibilityTest by creating(JvmTestSuite::class) {
            useJUnitJupiter()

            dependencies {
                implementation(sourceSetOutput("develocityCompatibility"))
                implementation(testFixtures(project()))
                implementation(platform(libs.junit.bom))
                implementation("org.junit.jupiter:junit-jupiter")
                implementation(libs.mockito.core)
                implementation(libs.mockito.jupiter)
            }
        }
    }
}

tasks.named("check") {
    dependsOn(testing.suites["enterpriseCompatibilityTest"])
    dependsOn(testing.suites["develocityCompatibilityTest"])
}

publishing {
    publications.create<MavenPublication>("mavenJava") {
        groupId = "com.gradle"
        artifactId = project.name
        from(components["java"])

        pom {
            name = publicationExtension.name
            description = publicationExtension.description
            url.set("https://github.com/gradle/develocity-api-adapters/")
            licenses {
                license {
                    name.set("Apache-2.0")
                    url.set("https://www.apache.org/licenses/LICENSE-2.0")
                }
            }
            developers {
                developer {
                    name.set("The Gradle team")
                    organization.set("Gradle Inc.")
                    organizationUrl.set("https://gradle.com")
                }
            }
            scm {
                connection.set("scm:git:git://github.com/gradle/develocity-api-adapters.git")
                developerConnection.set("scm:git:ssh://git@github.com:gradle/develocity-api-adapters.git")
                url.set("https://github.com/gradle/develocity-api-adapters/")
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
    useInMemoryPgpKeys(System.getenv("PGP_SIGNING_KEY"), System.getenv("PGP_SIGNING_KEY_PASSPHRASE"))
}
