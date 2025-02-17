version = "2023.05"

project {
    // Project ID and name
    id("HelloWorld_K8s")
    name = "HelloWorld Kubernetes Project"

    // Build configuration
    buildType {
        id("BuildAndDeploy")
        name = "Build Docker Image and Deploy to Kubernetes"

        // VCS root
        vcs {
            root(TeamCityVcsRoot) // Replace with your VCS root ID
        }

        // Build steps
        steps {
            // Step 1: Build Docker image
            script {
                name = "Build Docker Image"
                scriptContent = """
                    docker build -t your-dockerhub-username/hello-world:latest .
                    docker push your-dockerhub-username/hello-world:latest
                """.trimIndent()
            }

            // Step 2: Deploy Helm chart
            script {
                name = "Deploy Helm Chart"
                scriptContent = """
                    helm upgrade --install hello-world ./hello-world --namespace default
                """.trimIndent()
            }

            // Step 3: Verify deployment
            script {
                name = "Verify Deployment"
                scriptContent = """
                    kubectl get pods -n default
                    kubectl get svc -n default
                """.trimIndent()
            }
        }

        // Triggers
        triggers {
            vcs {
                branchFilter = "+:refs/heads/main" // Trigger on changes to the main branch
            }
        }
    }
}