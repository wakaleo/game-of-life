#!groovy
pipeline {
    agent any

    stages {
        stage('build') { 
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Results') {
                 post {
      always {
        junit '**/reports/junit/*.xml'
       }
     }
        }
stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
