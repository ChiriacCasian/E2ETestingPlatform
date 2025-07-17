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
    check(artifactRules == "%env.JOURNEY_NAME%.txt") {
        "Unexpected option value: artifactRules = $artifactRules"
    }
    artifactRules = """
        %env.JOURNEY_NAME%.txt
        +:**/*
    """.trimIndent()

    params {
        add {
            param("env.AGENTS_POOL", "Default")
        }
        add {
            param("JOURNEY_TYPE", "WEB")
        }
    }
    steps {
        update<ScriptBuildStep>(0) {
            clearConditions()
            scriptContent = """
                #!/usr/bin/env bash
                set -euo pipefail
                
                wget \
                  --method=POST \
                  -qO- http://localhost:3002/code  \
                	| jq -r '.code' > "${'$'}{JOURNEY_NAME}_script.py"
            """.trimIndent()
            param("teamcity.kubernetes.executor.pull.policy", "")
        }
        update<ScriptBuildStep>(1) {
            clearConditions()
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
            param("teamcity.kubernetes.executor.pull.policy", "")
        }
    }

    features {
        add {
            swabra {
            }
        }
    }

    requirements {
        add {
            doesNotExist("env.AGENT_NAME")
        }
    }
})

//object JourneyExecutorTemplate : Template({
//    name = "JourneyExecutorTemplate"
//
//    steps {
//        script {
//            name = "Run Journey"
//            scriptContent = File("JourneyExecutorStep.txt").readText().trimIndent()
//        }
//    }
//})