pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
            def mvnHome = tool 'Apache Maven 3.3.9'
            sh " '${mvnHome}/bin/mvn' compile"
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
