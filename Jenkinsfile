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
      
       AWS_ACCESS_KEY_ID=AKIAYA7GUKHLATBHUZWC AWS_SECRET_ACCESS_KEY=8mzEhHuU8R0QhHrd0nX5Om6GaQGJAm1Jwnzgh96j aws s3 cp *.war s3://madhu-sample
          '''
        

      }
    }
   stage('deploy') {
      steps {
      
                sh '''cd /var/lib/jenkins/workspace/game-of-life_develop/gameoflife-web/target && scp gameoflife.war jenkins@18.138.150.204:/home/jenkins/apache-tomcat-9.0.29/webapps'''

    }
  }
}
}


