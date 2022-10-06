node('JDK11-SPC') {
    stage('Sourceode') {
        git branch: '*/features', url: 'https://github.com/Prasadsgithub/game-of-life.git'
    }
    stage('build the code') {
        sh 'mvn package'
    }
}