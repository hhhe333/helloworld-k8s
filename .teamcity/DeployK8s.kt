import jetbrains.buildServer.configs.kotlin.v2019_2.*

object DeployK8s : BuildType({
    name = "Deploy to Kubernetes"

    steps {
        script {
            name = "Deploy Helm Chart"
            scriptContent = """
                helm upgrade --install hello-world ./hello-world --namespace default
            """.trimIndent()
        }
    }
})