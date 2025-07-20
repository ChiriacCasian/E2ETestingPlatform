package JourneyGeneration

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import java.nio.file.Path
import kotlin.io.path.readText

private const val RELATIVE_PATH = ".teamcity/JourneyGeneration/"

/**
 * JourneyGenerator
 */
object JourneyGenerator : BuildType({
    name = "JourneyGenerator"

    // there should be just one output from the journey Generator, the python script
    artifactRules = """
        +:*_script.py
    """.trimIndent()

    params {
        /// we may want to split generators on type, for example WEB_GENERATOR, or ANDROID_GENERATOR, depending on what phone/emulator they have access to
        param("env.COMPATIBLE_JOURNEY_TYPES", "GENERATOR")
        param("JOURNEY_TYPE", "WEB")
        param("env.JOURNEY_NAME", "Journey")
        /// TODO: move this token to secrets vault
        param("env.GIT_PAT_TOKEN", "eyJ0eXAiOiAiVENWMiJ9.OGEtUUxCVnBZN3NrY2NlWHdzdl9yMmZLM0ln.ZjUzNDI0NjUtOWIwZS00YjQyLThlZGQtZjEwNDZhMmEzNzI4")
        param("env.TEAMCITY_SERVER_URL", "%teamcity.serverUrl%")
        /// MANUALLY get a project's id from settings General Project ID
        param("env.PROJECT_PATH_ID", "%teamcity.project.id%")
    }

    steps {
        /**
         * todo: redo this step, add a build step like this where you publish to artifacts the index.html of the codegenFrontend
         * sleep 120
         * echo "##teamcity[publishArtifacts 'script/Journey2_script.py']"
         **/
        script {
            id = "Generate Journey Script"
            scriptContent = Path.of(RELATIVE_PATH + id).readText()
        }
        script {
            id = "Generate Journey build configuration"
            scriptContent = Path.of(RELATIVE_PATH + id).readText()
        }
    }

    features {
        swabra {
        }
    }

    requirements {
        contains("env.COMPATIBLE_JOURNEY_TYPES", "WEB", "AGENT_REQUIREMENT")
    }
})