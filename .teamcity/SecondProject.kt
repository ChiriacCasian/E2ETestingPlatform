import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.buildReportTab

object SecondProject : Project({
    name = "Second_project"
    id("SecondProject")

    vcsRoot(HttpsGithubComChiriacCasianTCBuildServerGit)

    buildType(JourneyMakerV1)

    buildType(JourneyMakerV2)

    template(JourneyExecutorTemplate)

//    features {
//        buildReportTab {
//            id = "ID_OF_BUILD_REPORT_TAB"
//            title = "Report"
//            startPage = "report/index.html"
//        }
//    }
})

object JourneyMakerV2 : BuildType({
    name = "JourneyMakerV2"
    id("JourneyMakerV2")
})