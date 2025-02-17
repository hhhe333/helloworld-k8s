import jetbrains.buildServer.configs.kotlin.v2019_2.*

object DeployK8s : BuildType({
    name = "Deploy to Kubernetes"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        script {
            name = "Deploy using Helm"
            scriptContent = """
                helm upgrade --install hello-world ./chart \
                  --set image.repository=my-docker-registry/hello-world \
                  --set image.tag=latest
            """
        }
    }
})
