package .teamcity.Deploy

import jetbrains.buildServer.configs.kotlin.v2023_2.*
import jetbrains.buildServer.configs.kotlin.v2023_2.buildSteps.script

object DeployK8S : BuildType({
    name = "Deploy to Kubernetes"

    // Load the password parameter
    params {
        password("deploy.k8s.secretPassword", "credentialsJSON")  // Mask it in logs
    }

    vcs {
        root(DslContext.settingsRoot)  // Uses the current Git repository
    }

    steps {
        // Step 1: Install Helm & Kubectl
        script {
            name = "Install Helm & Kubectl"
            scriptContent = """
                curl -LO "https://get.helm.sh/helm-v3.9.0-linux-amd64.tar.gz"
                tar -zxvf helm-v3.9.0-linux-amd64.tar.gz
                mv linux-amd64/helm /usr/local/bin/helm
                
                curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
                chmod +x kubectl
                mv kubectl /usr/local/bin/kubectl
            """.trimIndent()
        }

        // Step 2: Configure Kubernetes Context
        script {
            name = "Configure Kubernetes Context"
            scriptContent = """
                export KUBECONFIG=$HOME/.kube/config
                kubectl cluster-info
            """.trimIndent()
        }

        // Step 3: Deploy Helm Chart with Password
        script {
            name = "Deploy Helm Chart"
            scriptContent = """
                echo "Deploying with secret %env.my_test%"
                helm upgrade --install hello-world ./hello-world \
                    --set secret.password="%deploy.k8s.secretPassword%"
            """.trimIndent()
        }
    }

    // Triggers the build when new code is pushed
    triggers {
        vcs {
            branchFilter = "+:*"
        }
    }
})
