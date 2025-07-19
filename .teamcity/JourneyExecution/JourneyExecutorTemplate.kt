package JourneyExecution

import jetbrains.buildServer.configs.kotlin.Template
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import java.io.File

object JourneyExecutorTemplate : Template({
    name = "JourneyExecutorTemplate"

    artifactRules = """
        +:report=>report
        +:script=>script
    """.trimIndent()

    steps {
        script {
            id = "Execute Journey Script"
            scriptContent = File(id!!).readText().trimIndent()
        }
    }
}
)