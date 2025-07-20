package JourneyExecution

import jetbrains.buildServer.configs.kotlin.Template
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import java.nio.file.Path
import kotlin.io.path.readText

private const val RELATIVE_PATH = "JourneyExecution/"

object JourneyExecutorTemplate : Template({
    name = "JourneyExecutorTemplate"

    artifactRules = """
        +:report=>report
        +:script=>script
    """.trimIndent()

    steps {
        script {
            id = "Execute Journey Script"
            scriptContent = Path.of(RELATIVE_PATH + id).readText()
        }
    }
}
)