node('master'){
    stage('scm'){
        git 'https://github.com/devopstrainingvenkat/game-of-life'

    }

    stage('build'){
        
        sh label: '', script: 'mvn package'
        input 'continue to next step?'
    }

    stage('postbuild'){
        
        junit 'gameoflife-web/target/surefire-reports/*.xml'
        archiveArtifacts artifacts: 'gameoflife-web/target/*.war', followSymlinks: false

    }

}
