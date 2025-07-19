import JourneyExecution.JourneyExecutorTemplate
import JourneyGeneration.JourneyGenerator
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.buildReportTab

object Root_Project : Project({

    vcsRoot(HttpsGithubComChiriacCasianTCBuildServerGit)

    buildType(JourneyGenerator)

    template(JourneyExecutorTemplate)

    features {
        buildReportTab {
            id = "REPORT_TAB"
            title = "Report"
            startPage = "report/index.html"
        }

        buildReportTab {
            id = "SCRIPT_GENERATION_TAB"
            title = "Generate Journey"
            startPage = "Journey2_script.py"
        }
    }
})