import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.buildReportTab

object SecondProject : Project({
    name = "SecondProject"

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