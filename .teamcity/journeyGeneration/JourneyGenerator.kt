package journeyGeneration

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import java.nio.file.Path
import kotlin.io.path.readText

private const val RELATIVE_PATH = "journeyGeneration/"

object JourneyGenerator : BuildType({
    name = "JourneyGenerator"

    // there should be just one output from the journey Generator, the python script
    artifactRules = """
        +:*_script.py
    """.trimIndent()

    params {
        /// we may want to split generators on type, for example WEB_GENERATOR, or ANDROID_GENERATOR, depending on what phone/emulator they have access to
        param("env.CODEGEN_SERVER_URL", "http://host.docker.internal:3002/code")
        param("env.CODEGEN_FRONTEND_URL", "http://localhost:4200")
        param("env.JOURNEY_TYPE", "WEB")
        param("env.JOURNEY_NAME", "Journey")
        param("env.TEAMCITY_SERVER_URL", "%teamcity.serverUrl%")
        param("env.PROJECT_PATH_ID", "%teamcity.project.id%")
    }

    steps {
        /**
         * todo: redo this step, add a build step like this where you publish to artifacts the index.html of the codegenFrontend
         * sleep 120
         * echo "##teamcity[publishArtifacts 'script/Journey2_script.py']"
         **/
        script {
            id = "journeyGeneration/Generate Journey Script"
            scriptContent = Path.of( id!!).readText()
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
})