import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import java.io.File
import jetbrains.buildServer.configs.kotlin.projectFeatures.buildReportTab


object JourneyMakerV1 : BuildType({
    name = "JourneyMaker_v1"

    artifactRules = "%env.JOURNEY_NAME%.txt"

    params {
        param("JOURNEY_SCRIPT", "")
        param("env.JOURNEY_NAME", "DEFAULT NAME")
        param("env.GIT_PAT_TOKEN", "")
    }

    steps {
        script {
            id = "simpleRunner"
            scriptContent = """
                #!/usr/bin/env bash
                set -euo pipefail
                
                # ------------------------------------------------------------------
                # 1. Compose the target file name
                #    JOURNEY_NAME should be defined as an Environment / System
                #    parameter so it is available as ${'$'}JOURNEY_NAME
                # ------------------------------------------------------------------
                FILE_NAME="${'$'}{JOURNEY_NAME}.txt"
                
                # ------------------------------------------------------------------
                # 2. Write the parameter value into the file
                #    %JOURNEY_SCRIPT% is substituted by TeamCity → becomes a literal
                #    string inside the here-document, preserving line breaks.
                # ------------------------------------------------------------------
                cat > "${'$'}{FILE_NAME}" <<'EOF'
                %JOURNEY_SCRIPT%
                EOF
            """.trimIndent()
        }
        script {
            name = "Generate Journey Build"
            scriptContent = File("secondStepScript.txt").readText().trimIndent()
        }

    }
})

object JourneyExecutorTemplate : Template({
    name = "JourneyExecutorTemplate"

    steps {
        script {
            name = "Run Journey"
            scriptContent = File("JourneyExecutorStep.txt").readText().trimIndent()
        }
    }
})