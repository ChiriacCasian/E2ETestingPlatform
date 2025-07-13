import jetbrains.buildServer.configs.kotlin.*

object Journey_ThirdGeneratedBuild : BuildType({
    name = "ThirdGeneratedBuild"

    templates(JourneyExecutorTemplate)
})
