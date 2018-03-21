pipeline {
  agent any
  stages {
    stage('compile') {
      steps {
        sh 'mvn compile'
      }
    }
    stage('test') {
      steps {
        sh 'mvn test'
      }
    }
    stage('review') {
      steps {
        sh 'mvn -P metrics pmd:pmd'
      }
    }
  }
}