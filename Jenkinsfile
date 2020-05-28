node('devops'){
    stage('scm'){
         git 'https://github.com/ramdaspallyp/game-of-life.git'
    }
        stage('build'){
            sh label: '', script: 'mvn package'
        }
            stage('postbuild'){
                junit 'gameoflife-web/target/surefire-reports/*.xml'
                archiveArtifacts 'gameoflife-web/target/*.war'
            
            }
    
}
