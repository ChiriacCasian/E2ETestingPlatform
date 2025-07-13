import jetbrains.buildServer.configs.kotlin.*
import Project_A.newJourneyBuild

object Journey_FirstGeneratedJourney : BuildType(
newJourneyBuild("FirstGeneratedJourney", "FirstGeneratedJourney")
)
