pipeline {
    agent {label 'MAVEN_JDK8'}
    triggers { cron ('H/30 * * * *') }
    parameters {
         string(name: 'MAVEN_GOAL', defaultValue: 'PACKAGE', description: 'MAVEN_GOAL') 
    }
    stages {
        stage('vcs') {
            steps {
                git url: 'https://github.com/saurabhmarch23/game-of-life.git',
                    branch: 'declarative'

                }
            }
        stage('package')  {
            tools {
                jdk 'JDK_8_UBUNTU'
            }
            steps {
                sh "mvn ${params.MAVEN_GOAL}"
            }
        }
        stage('post build') {
            steps {
                archiveArtifacts artifacts: '**/target/gameoflife.war',
                                 onlyIfSuccessful: true
                junit testResults: '**/surefire-reports/TEST-*.xml'
            }
        }
    }
}
