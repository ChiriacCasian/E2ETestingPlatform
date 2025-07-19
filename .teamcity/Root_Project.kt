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
            startPage = "index.html"
        }
    }
})