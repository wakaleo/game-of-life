node ('SAGARLINUX'){
    stage ('scm'){
        git 'https://github.com/wakaleo/game-of-life.git'
    }
    stage('build'){
        sh 'mvn package'
    }
}

