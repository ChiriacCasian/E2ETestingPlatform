import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

// TODO: move auth data to secrets vault
object TCBuildServerGit : GitVcsRoot({
    name = "%TC_BUILDSERVER_GIT_ADDRESS%"
    url = "%TC_BUILDSERVER_GIT_ADDRESS%"
    branch = "refs/heads/main"
    authMethod = password {
        userName = "%GIT_USER%"
        password = "credentialsJSON:9b4689e5-3c42-4bfb-843e-7d0d1a023a38"
    }
})

object JourneyExecutorPodInfraV2Git : GitVcsRoot({
    name = "%JOURNEY_EXECUTOR_GIT_ADDRESS%"
    url = "%JOURNEY_EXECUTOR_GIT_ADDRESS%"
//    url = "https://github.com/ChiriacCasian/pod-infra_v2"
    branch = "refs/heads/master"
    authMethod = password {
        userName = "%GIT_USER%"
        password = "%GIT_AUTH_TOKEN%"
    }
    branchSpec = "refs/heads/*" /// this is the vcs trigger
})