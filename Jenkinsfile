node {
    stage('SCM'){
        git 'https://github.com/wakaleo/game-of-life.git'
    }
	
    stage('Build & Package'){
        sh 'mvn clean package'
    }
	
    stage('Results'){
        archive 'gameoflife-web/target/gameoflife.war'
        junit 'gameoflife-web/target/surefire-reports/*.xml'
    }
}
