import HttpsGithubComChiriacCasianTCBuildServerGit.param
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import java.io.File
import jetbrains.buildServer.configs.kotlin.projectFeatures.buildReportTab
import jetbrains.buildServer.configs.kotlin.ui.add
import jetbrains.buildServer.configs.kotlin.ui.update


object JourneyMakerV1 : BuildType({
    artifactRules = """
        %env.JOURNEY_NAME%.txt
        +:**/*
    """.trimIndent()

    params {
        param("env.AGENTS_POOL", "Default")
        param("JOURNEY_TYPE", "WEB")
    }
    steps {
        script {
            scriptContent = """
                #!/usr/bin/env bash
                set -euo pipefail
                
                wget \
                  --method=POST \
                  -qO- http://localhost:3002/code  \
                	| jq -r '.code' > "${'$'}{JOURNEY_NAME}_script.py"
            """.trimIndent()
        }
        script {
            scriptContent = """
                payload=${'$'}(cat <<EOF
                {
                  "id"        : "SecondProject_Journey_${'$'}{JOURNEY_NAME}",
                  "name"      : "${'$'}{JOURNEY_NAME}",
                  "projectId" : "SecondProject",
                  "template": { "id": "SecondProject_JourneyExecutorTemplate" },
                  
                	"artifact-dependencies": {
                    "count": 1,
                    "artifact-dependency": [
                      {
                        "id": "ARTIFACT_DEPENDENCY_2",
                        "type": "artifact_dependency",
                        "properties": {
                          "property": [
                            {
                              "name": "cleanDestinationDirectory",
                              "value": "false"
                            },
                            {
                              "name": "pathRules",
                              "value": "${'$'}{JOURNEY_NAME}_script.py=>script"
                            },
                            {
                              "name": "revisionName",
                              "value": "buildNumber"
                            },
                            {
                              "name": "revisionValue",
                              "value": "%system.build.number%"
                            }
                          ],
                          "count": 4
                        },
                        "source-buildType": {
                          "id": "SecondProject_JourneyMakerV1"
                        }
                      }
                    ]
                  },
                 
                 "features": {
                    "count": 1,
                    "feature": [
                      {
                        "id": "swabra",
                        "type": "swabra",
                        "properties": {
                          "property": [
                            {
                              "name": "swabra.enabled",
                              "value": "swabra.before.build"
                            }
                          ],
                          "count": 1
                        }
                      }
                    ]
                  },
                  	"agent-requirements": {
                    "count": 1,
                    "agent-requirement": [
                      {
                        "id": "RQ_6",
                        "type": "contains",
                        "properties": {
                          "property": [
                            {
                              "name": "property-name",
                              "value": "env.COMPATIBLE_JOURNEY_TYPES"
                            },
                            {
                              "name": "property-value",
                              "value": "%JOURNEY_TYPE%"
                            }
                          ],
                          "count": 2
                        }
                      }
                    ]
                  }
                
                
                }
                EOF
                )
                
                curl -i -sS \
                  -H "Authorization: Bearer ${'$'}{GIT_PAT_TOKEN}" \
                  -H "Content-Type: application/json" \
                  -X POST \
                  "localhost:8111/app/rest/buildTypes" \
                  -d "${'$'}{payload}"
            """.trimIndent()
        }
    }

    features {
        swabra {
        }
    }

    requirements {
        doesNotExist("env.AGENT_NAME")
    }
})

object JourneyExecutorTemplate : Template({
    name = "JourneyExecutorTemplate"

    steps {
        script {
            id = "Execute Journey Script"
            scriptContent = """
                #!/usr/bin/env bash
                set -euo pipefail
                shopt -s nullglob          # ignore the glob if the file is missing
                
                ART_DIR="./script"
                
                # Expect exactly one .txt file in ART_DIR
                txt_files=("${'$'}ART_DIR"/*.py)
                if [[ ${'$'}{#txt_files[@]} -ne 1 ]]; then
                  echo "Error: expected exactly one .txt file in ${'$'}ART_DIR, found ${'$'}{#txt_files[@]}." >&2
                  exit 1
                fi
                
                # Absolute path (realpath first, fallback to readlink -f)
                abs_path="${'$'}(realpath "${'$'}{txt_files[0]}" 2>/dev/null || readlink -f "${'$'}{txt_files[0]}")"
                echo "Script found: ${'$'}abs_path"
                
                # Call the endpoint — adjust URL / headers to match your service
                curl -X POST \
                     --data-urlencode "scriptPath=${'$'}abs_path" \
                     --data-urlencode "type=WEB" \
                     http://localhost:8060/runJourney
            """.trimIndent()
        }
    }
}
)