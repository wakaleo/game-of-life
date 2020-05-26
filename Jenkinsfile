pipeline {
  agent any
  stages {
    stage('Clean') {
      steps {
        echo 'cleaning  the workspace'
        sh 'echo "running the step for shell"'
      }
    }

    stage('Build') {
      parallel {
        stage('Build') {
          steps {
            build ' project10'
          }
        }

        stage('ParallelBuild') {
          steps {
            sleep 10
          }
        }

      }
    }

    stage('Ending') {
      steps {
        mail(subject: 'Pipeline', body: 'Pipeine Body', from: 'kudipudijahnavi2410@gmail.com', to: 'kudipudijahnavi2410@gmail.com')
      }
    }

  }
}