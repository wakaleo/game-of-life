pipeline {
    agent { label 'NODE1'}
    tools { 
        jdk 'JDK_8_MAVEN'

    }
    stages {
        stage ( ' Version Control System'){
            steps {
                git url: 'https://github.com/nareshdevops1237/game-of-life.git',
                    branch: 'master'
            }
        }
        stage ( ' Build Maven package') {
            steps {
                sh 'mvn  clean package'
                
            }
        }
    }

}