pipeline {
    agent any
    tools {
        maven 'MAVEN_NODE1'
        jdk "JDK_8_MAVEN"
    }
    stages {
        stage ( ' Version Control System'){
            steps {
                git url: 'https://github.com/nareshdevops1237/game-of-life.git',
                    branch: master
            }
        }
    }

}