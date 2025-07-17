import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.buildReportTab

object SecondProject : Project({
    name = "Second_Project"
    id("SecondProject")

    vcsRoot(HttpsGithubComChiriacCasianTCBuildServerGit)

    buildType(JourneyMakerV1)

    template(JourneyExecutorTemplate)

    features {
        buildReportTab {
            id = "ID_OF_BUILD_REPORT_TAB"
            title = "Report"
            startPage = "report/index.html"
        }
    }
})