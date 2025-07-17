import HttpsGithubComChiriacCasianTCBuildServerGit
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.buildReportTab

object TC_Custom_Project : Project({
    name = "TC_Custom_Project"
    description = "TC_Custom_Project"

    vcsRoot(HttpsGithubComChiriacCasianTCBuildServerGit)

    buildType(JourneyMakerV1)

    template(JourneyExecutorTemplate)

    buildType(Journey_FirstGeneratedJourney)

    features {
        buildReportTab {
            id = "ID_OF_BUILD_REPORT_TAB"
            title = "Report"
            startPage = "report/index.html"
        }
    }
})