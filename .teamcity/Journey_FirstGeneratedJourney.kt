import jetbrains.buildServer.configs.kotlin.*

object Journey_FirstGeneratedJourney : BuildType({
    templates(JourneyExecutorTemplate)
})
