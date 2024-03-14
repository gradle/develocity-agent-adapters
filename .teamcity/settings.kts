import jetbrains.buildServer.configs.kotlin.v2019_2.AbsoluteId
import jetbrains.buildServer.configs.kotlin.v2019_2.CheckoutMode
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.RelativeId
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2019_2.project
import jetbrains.buildServer.configs.kotlin.v2019_2.toId
import jetbrains.buildServer.configs.kotlin.v2019_2.version

version = "2022.04"

project {
    publishDevelocityApiAdapters(
        "develocity-gradle-plugin-adapters",
        "Develocity Gradle Plugin Adapters"
    )
    publishDevelocityApiAdapters(
        "develocity-maven-extension-adapters",
        "Develocity Maven Extension Adapters"
    )
    params {
        param("env.GRADLE_ENTERPRISE_ACCESS_KEY", "%ge.gradle.org.access.key%")
        param("env.GRADLE_CACHE_REMOTE_URL", "%gradle.cache.remote.url%")
        param("env.GRADLE_CACHE_REMOTE_USERNAME", "%gradle.cache.remote.username%")
        password("env.GRADLE_CACHE_REMOTE_PASSWORD", "%gradle.cache.remote.password%")
    }
}

fun Project.publishDevelocityApiAdapters(projectPath: String, projectDisplayName: String) {
    buildType {
        name = "Publish ${projectDisplayName}"
        id = RelativeId(name.toId())
        description = "Publish ${projectDisplayName} to Maven Central staging repository"

        vcs {
            root(AbsoluteId("OpenSourceProjects_DevelocityApiAdapters_GradleDevelocityApiAdapters"))
            checkoutMode = CheckoutMode.ON_AGENT
            cleanCheckout = true
        }

        requirements {
            contains("teamcity.agent.jvm.os.name", "Linux")
        }

        steps {
            gradle {
                useGradleWrapper = true
                tasks = "clean :${projectPath}:publishMavenJavaPublicationToSonatypeRepository"
                gradleParams = "--build-cache"
            }
        }
        params {
            param("env.JDK8", "%linux.java8.oracle.64bit%")
            param("env.JAVA_HOME", "%linux.java21.openjdk.64bit%")
            param("env.ORG_GRADLE_PROJECT_sonatypeUsername", "%mavenCentralStagingRepoUser%")
            password("env.ORG_GRADLE_PROJECT_sonatypePassword", "%mavenCentralStagingRepoPassword%")
            password("env.PGP_SIGNING_KEY", "%pgpSigningKey%")
            password("env.PGP_SIGNING_KEY_PASSPHRASE", "%pgpSigningPassphrase%")
        }
    }
}
