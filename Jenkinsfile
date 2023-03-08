pipeline {
    agent { label 'NODE1'}
    stages {
        stage ( ' Version Control System'){
            steps {
                git url: 'https://github.com/nareshdevops1237/game-of-life.git',
                    branch: 'master'
            }
        }
    }

}