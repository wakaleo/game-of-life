pipeline {
  agent any
  stages {
    stage('checkout') {
      steps {
        git 'https://github.com/madhurichittabathina/game-of-life.git'
      }
    }
    stage('build') {
      steps {
        sh 'mvn clean package'
      }
    }
    stage('test') {
      steps {
        sh 'mvn test'
      }
    }
    stage('sonar') {
      steps {
        sh 'mvn sonar:sonar'
      }
    }
    stage('deploy') {
      steps {
        sh 'sudo cp -f /var/lib/jenkins/workspace/pipeline/gameoflife-web/target/gameoflife.war /root/apache-tomcat-9.0.27/webapps'
      }
    }
  }
}