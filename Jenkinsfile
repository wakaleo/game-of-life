node('master'){
    
    parameters{
        string(name: 'BRANCH_TO_BUILD' , defaultValue: 'master' , description: 'Enter branch to be built')
    }
    stage('scm'){
        git 'https://github.com/devopstrainingvenkat/game-of-life'

    }

    stage('build'){
        
        sh label: '', script: 'mvn package'
        input 'continue to next step?'
    }
    
    stage('Sonar') {
        withSonarQubeEnv('SONAR-6.7.4') {
             sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.6.0.1398:sonar'
        }

    stage('postbuild'){
        
        junit 'gameoflife-web/target/surefire-reports/*.xml'
        archiveArtifacts artifacts: 'gameoflife-web/target/*.war', followSymlinks: false

    }

}
