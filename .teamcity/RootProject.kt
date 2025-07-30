import journeyExecution.JourneyTemplate
import journeyGeneration.JourneyGenerator
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.buildReportTab
import journeyExecution.JourneyExecutor
import webpagesPlugin.WebpagesPlugin

object RootProject : Project({

    vcsRoot(TCBuildServerGit)
    vcsRoot(JourneyExecutorPodInfraV2Git)
    vcsRoot(WebpagesPluginGit)

    buildType(JourneyGenerator)
    buildType(JourneyExecutor)
    buildType(WebpagesPlugin)

    template(JourneyTemplate)

    features {
        buildReportTab {
            id = "REPORT_TAB"
            title = "Report"
            startPage = "report/index.html"
        }

        buildReportTab {
            id = "SCRIPT_GENERATION_TAB"
            title = "Generate Journey"
            startPage = "index.html"
        }
    }
})