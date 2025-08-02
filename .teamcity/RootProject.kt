import journeyExecution.JourneyTemplate
import journeyGeneration.JourneyGenerator
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.buildReportTab
import journeyExecution.JourneyExecutor

object RootProject : Project({

    vcsRoot(TCBuildServerGit)
    vcsRoot(JourneyExecutorPodInfraV2Git)

    buildType(JourneyGenerator)
    buildType(JourneyExecutor)

    template(JourneyTemplate)

    params {
        param("TC_BUILDSERVER_GIT_ADDRESS", "https://github.com/ChiriacCasian/TeamCityBuildServer")
        param("JOURNEY_EXECUTOR_GIT_ADDRESS", "https://github.com/ChiriacCasian/pod-infra_v2")
        param("GIT_USER", "ChiriacCasian")
        password("GIT_AUTH_TOKEN", "credentialsJSON:f1f20dd4-912c-4977-ae90-c79dc6674b3e")
        password("TC_AUTH_TOKEN", "credentialsJSON:9ee1fb21-faef-4c3c-9772-988c459466c2")
    }

    features {
        buildReportTab {
            id = "REPORT_TAB"
            title = "Report"
            startPage = "report/index.html"
        }
    }
})