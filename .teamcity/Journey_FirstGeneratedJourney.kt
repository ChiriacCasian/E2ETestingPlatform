import jetbrains.buildServer.configs.kotlin.*

object Journey_FirstGeneratedJourney : BuildType({
    name = "FirstGeneratedJourney"

    templates(JourneyExecutorTemplate)
})
