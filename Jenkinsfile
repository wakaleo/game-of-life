node {
    stage('SCM') {
        git 'https://github.com/sridevops-yannam/game-of-life.git'
    }
    
    stage('Build & Package') {
        withSonarQubeEnv('SONAR') {
            sh 'mvn clean package sonar:sonar'
        }
    }
    
    stage("Quality Gate") {
        timeout(time: 1, unit: 'HOURS') {
              def qg = waitForQualityGate()
              if (qg.status != 'OK') {
                  error "Pipeline aborted due to quality gate failure: ${qg.status}"
              }
          }
    }
    
    stage('Results'){
        archive 'gameoflife-web/target/gameoflife.war'
        junit 'gameoflife-web/target/surefire-reports/*.xml'
    }
}