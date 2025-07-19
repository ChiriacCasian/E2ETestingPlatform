import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.buildReportTab

object Root_Project : Project({

    vcsRoot(HttpsGithubComChiriacCasianTCBuildServerGit)

    buildType(JourneyMakerV1)

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
            startPage = "script/Journey2_script.py"
        }
    }
})