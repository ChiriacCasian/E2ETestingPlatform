import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

// TODO: move auth data to secrets vault
object TCBuildServerGit : GitVcsRoot({
    name = "https://github.com/ChiriacCasian/TCBuildServer.git"
    url = "https://github.com/ChiriacCasian/TeamCityBuildServer"
    branch = "refs/heads/main"
    authMethod = password {
        userName = "ChiriacCasian"
        password = "credentialsJSON:9b4689e5-3c42-4bfb-843e-7d0d1a023a38"
    }
})

object JourneyExecutorPodInfraV2Git : GitVcsRoot({
    name = "https://github.com/ChiriacCasian/pod-infra_v2.git"
    url = "https://github.com/ChiriacCasian/pod-infra_v2"
    branch = "refs/heads/master"
    authMethod = password {
        userName = "ChiriacCasian"
        password = "credentialsJSON:9b4689e5-3c42-4bfb-843e-7d0d1a023a38"
    }
    branchSpec = "refs/heads/*" /// this is the vcs trigger
})