import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import java.io.File


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
            scriptContent = """
                #!/usr/bin/env bash
set -euo pipefail
shopt -s nullglob          # ignore the glob if the file is missing

ART_DIR="./scripts"

# Expect exactly one .txt file in ART_DIR
txt_files=("$ART_DIR"/*14.txt)
if [[ ${#txt_files[@]} -ne 1 ]]; then
  echo "Error: expected exactly one .txt file in $ART_DIR, found ${#txt_files[@]}." >&2
  exit 1
fi

# Absolute path (realpath first, fallback to readlink -f)
abs_path="${'$'}(realpath "${txt_files[0]}" 2>/dev/null || readlink -f "${txt_files[0]}")"
echo "Script found: $abs_path"

# Call the endpoint — adjust URL / headers to match your service
curl -X POST \
     --data-urlencode "scriptPath=$abs_path" \
     --data-urlencode "type=WEB" \
     http://localhost:8060/runJourney
            """.trimIndent()
        }
    }
})