package journeyExecution

import jetbrains.buildServer.configs.kotlin.Template
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import journeyExecution.JourneyExecutor.artifactRules
import java.nio.file.Path
import kotlin.io.path.readText

private const val RELATIVE_PATH = "journeyExecution/"

object JourneyTemplate : Template({
    name = "JourneyTemplate"

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

    dependencies {
        artifacts(JourneyExecutor) {
            buildRule = lastSuccessful()
            artifactRules = "+:journeyExecutor.jar"
            cleanDestination = true
        }
    }
}
)