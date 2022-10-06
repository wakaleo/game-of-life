node('JDK11-SPC') {
    stage('SourceCode') {
        //get the code from git repo for the required branch
        git branch: '*/features', url: 'https://github.com/Prasadsgithub/game-of-life.git'
    }

    stage('Build the code') {
        sh 'mvn --package'
    }

    stage('Archiving and Test Results') {
        junit '**/surefire-reports/*.xml'
    }
    
}