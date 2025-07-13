import JourneyTemplate
import jetbrains.buildServer.configs.kotlin.*

fun newJourneyBuild(idSuffix: String, journeyName: String): BuildType =
    BuildType {
        id("Journey_$idSuffix")
        name = "Journey – $journeyName"
        templates(JourneyTemplate)
        params {
            param("journeyName", journeyName)
        }
        // optionally: triggers/snapshot dependencies can also be put here
    }
