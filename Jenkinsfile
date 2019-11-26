pipeline {
  agent Pre-prod
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
      
       AWS_ACCESS_KEY_ID=AKIAYA7GUKHLEMTJTB7B AWS_SECRET_ACCESS_KEY=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 aws s3 cp *.war s3://ajayvarma-s3
          '''
        ######### https://ajayvarma-s3.s3.amazonaws.com/gameoflife.war

      }
    }
    stage('deploy') {
      steps {
        sh 'cp -f /var/lib/jenkins/workspace/pipeline/gameoflife-web/target/gameoflife.war /root/apache-tomcat-9.0.29/webapps'
      }
    }
  }
}
