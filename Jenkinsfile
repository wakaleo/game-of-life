pipeline {
    agent { label 'node1'}
    triggers { cron('* * * * 1-5') }
    stages {
        stage ('scm') {
            steps {
                git 'https://github.com/eswarsgithub/game-of-life.git'
            }
        }
        stage ('compile') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}
