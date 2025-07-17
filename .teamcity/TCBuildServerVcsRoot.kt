import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object HttpsGithubComChiriacCasianTCBuildServerGit : GitVcsRoot({
    name = "https://github.com/ChiriacCasian/TCBuildServer.git"
    url = "https://github.com/ChiriacCasian/TeamCityBuildServer"
    branch = "refs/heads/main"
    authMethod = password {
        userName = "ChiriacCasian"
        password = "credentialsJSON:9b4689e5-3c42-4bfb-843e-7d0d1a023a38"
    }
})