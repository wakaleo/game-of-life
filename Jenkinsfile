#!groovy
pipeline {
    agent {label 'test02'}

    stages {
        stage('Build') { 
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
                step([$class: 'JUnitResultArchiver', keepLongStdio: true, testResults: '**/target/surefire-reports/TEST*.xml'])
            }
        }
    stage('Deploy') {
            steps {
                sh 'mvn clean tomcat7:deploy'
            }
     }
    }
}
