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
            steps {
                sh ' step([$class: \'JUnitResultArchiver\', keepLongStdio: true, testResults: \'target/test-reports/TEST*.xml\'])'
            }
        }
    }
}
