node('master'){
    stage('scm'){
        git 'https://github.com/devopstrainingvenkat/game-of-life'

    }

    stage('build'){
        
        sh label: '', script: 'mvn package'

    }

    stage('postbuild'){
        
        junit 'gameoflife-web/target/surefire-reports/*.xml'
        archiveArtifacts artifacts: 'gameoflife-web/target/*.war', followSymlinks: false

    }

}
