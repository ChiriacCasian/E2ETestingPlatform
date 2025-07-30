package webpagesPlugin

import WebpagesPluginGit
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.TeamCityDsl
import jetbrains.buildServer.configs.kotlin.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.buildSteps.maven

object WebpagesPlugin : BuildType( {
    name = "Webpages Plugin"
    description = "Plugin that allows us to integrate JourneyGenerator into teamcity"

    artifactRules= """
        +:target/WebpagesInTeamCityPlugin.zip
    """.trimIndent()

    vcs { root(WebpagesPluginGit) }

    params {
        param("journey.generator.address" ,"\${system:journey.generator.address}")
    }

    steps {
        maven {
            name = "Maven package"

            goals = "package"
            pomLocation = "pom.xml"
        }

    }

    features {
        swabra {
        }
    }
})