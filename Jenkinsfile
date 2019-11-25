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
     stage('s3') {
      steps {
        sh '''cd /var/lib/jenkins/workspace/game-of-life_develop/gameoflife-web/target/
        #check that 
        if[gameoflife.war] 
        then 
        echo war exists
        else
          echo war not present
        fi
        aws s3 cp *.war s3://ajayvarma-s3/
          '''
        

      }
    }
    stage('deploy') {
      steps {
        sh 'cp -f /var/lib/jenkins/workspace/pipeline/gameoflife-web/target/gameoflife.war /root/apache-tomcat-9.0.27/webapps'
      }
    }
  }
}
