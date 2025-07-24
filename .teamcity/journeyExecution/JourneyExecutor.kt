package journeyExecution

import JourneyExecutorPodInfraV2Git
import RootProject.vcsRoot
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.buildSteps.gradle

object JourneyExecutor : BuildType( {
    name = "Journey Executor"
    description = "Latest artifact of this build is used to run Journeys on agents"

    artifactRules= """
        +:**/*-all.jar=>journeyExecutor.jar
    """.trimIndent()

    vcsRoot { JourneyExecutorPodInfraV2Git }

    steps {
        gradle {
            name = "Gradle build FatJar"

            tasks = "shadowJar"
            useGradleWrapper = true
            buildFile   = "app/build.gradle.kts"
        }
    }

    features {
        swabra {
        }
    }
})