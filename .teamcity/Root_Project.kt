import journeyExecution.JourneyTemplate
import journeyGeneration.JourneyGenerator
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.buildReportTab
import journeyExecution.JourneyExecutor

object Root_Project : Project({

    vcsRoot(TCBuildServerGit)

    buildType(JourneyGenerator)
    buildType(JourneyExecutor)

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