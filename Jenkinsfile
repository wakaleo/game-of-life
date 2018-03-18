pipeline {
  agent any
  stages {
    stage('compile') {
      steps {
        sh 'mvn compile'
      }
    }
    stage('build') {
      steps {
        sh 'mvn clean build'
      }
    }
  }
}