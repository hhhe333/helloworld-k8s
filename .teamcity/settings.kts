import jetbrains.buildServer.configs.kotlin.v2023_2.*

version = "2023.2"

project {
    // Define a secure parameter (password)
    params {
        param("deploy.k8s.secretPassword", "my-secure-password")  // Store securely in TeamCity
        password("env.my_test", "asdasdfasdfasdfadsfasdf")
    }

    buildType(DeployK8S)  // Register the DeployK8S build type
}
