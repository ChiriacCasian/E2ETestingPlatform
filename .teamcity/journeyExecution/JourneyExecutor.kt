package journeyExecution

import JourneyExecutorPodInfraV2Git
import RootProject.vcsRoot
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.triggers.vcs


object JourneyExecutor : BuildType( {
    name = "JourneyExecutor"
    description = "Latest artifact of this build is used to run Journeys on agents"

    artifactRules= """
        +:app-all.jar
    """.trimIndent()

    vcs { root(JourneyExecutorPodInfraV2Git) }

    steps {
        gradle {
            name = "Gradle build FatJar"

            tasks = "shadowJar"
            useGradleWrapper = true
            buildFile   = "app/build.gradle.kts"
        }
    }

    triggers {
        vcs {

        }
    }

    features {
        swabra {
        }
    }
})