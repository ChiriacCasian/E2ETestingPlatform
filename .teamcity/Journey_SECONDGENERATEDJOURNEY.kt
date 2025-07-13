import jetbrains.buildServer.configs.kotlin.*

object Journey_${JOURNEY_NAME} : BuildType({
    name = "${JOURNEY_NAME}"

    templates(JourneyExecutorTemplate)
})
