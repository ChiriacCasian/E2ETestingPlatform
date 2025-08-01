import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object TCBuildServerGit : GitVcsRoot({
    name = "%TC_BUILDSERVER_GIT_ADDRESS%"
    url = "%TC_BUILDSERVER_GIT_ADDRESS%"
    branch = "refs/heads/main"
    authMethod = password {
        userName = "%GIT_USER%"
        password = "%GIT_AUTH_TOKEN%"
    }
})

object JourneyExecutorPodInfraV2Git : GitVcsRoot({
    name = "%JOURNEY_EXECUTOR_GIT_ADDRESS%"
    url = "%JOURNEY_EXECUTOR_GIT_ADDRESS%"
    branch = "refs/heads/master"
    authMethod = token {
        userName = "%GIT_USER%"
        token = AuthMethod.Token("%GIT_AUTH_TOKEN%")
    }
    branchSpec = "refs/heads/*" /// this is the vcs trigger
})