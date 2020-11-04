node ('SAGARLINUX'){
    stage ('scm'){
        git 'https://github.com/wakaleo/game-of-life.git'
    }
    stage('build'){
        sh 'mvn package'
    }
    stage('postbuild'){
        archiveArtifacts artifacts: 'gameoflife-web /target/*.war', followSymlinks: false
        junit 'gameoflife-web /target/surefire-reports/*.xml'
    }
}
