import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.Project

object Second_project : Project({

    vcsRoot(HttpsGithubComChiriacCasianTCBuildServerGit)

    buildType(JourneyMakerV1)

    template(JourneyExecutorTemplate)

//    features {
//        buildReportTab {
//            id = "ID_OF_BUILD_REPORT_TAB"
//            title = "Report"
//            startPage = "report/index.html"
//        }
//    }
})