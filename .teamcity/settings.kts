import jetbrains.buildServer.configs.kotlin.v2019_2.*

version = "2022.04"

project {
    buildType(BuildDockerImage)
    buildType(DeployK8s)
}

object BuildDockerImage : BuildType({
    name = "Build Docker Image"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        script {
            name = "Build & Push Docker Image"
            scriptContent = """
                docker build -t my-docker-registry/hello-world:latest .
                docker push my-docker-registry/hello-world:latest
            """
        }
    }
})
