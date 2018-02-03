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
                 junit '**/target/surefire-reports/TEST-*.xml'
                 archive 'target/*.jar'
       }
     }

stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
