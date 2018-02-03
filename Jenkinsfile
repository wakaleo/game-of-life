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
                             
    step([$class: 'JUnitResultArchiver', keepLongStdio: true, testResults: '**/target/surefire-reports/TEST-*.xml'])
       }
     }
          stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }

