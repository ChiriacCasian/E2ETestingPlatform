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

object JourneyTemplate : Template({
    name = "RunJourneyTemplate"

    params {
        param("journeyName", "not-set-in-template")
    }

    steps {
        script {
            name = "Run Journey"
            scriptContent = """
                echo "Running journey: %journeyName%"
                # example: call Playwright / Cypress / etc.
            """.trimIndent()
        }
    }
})