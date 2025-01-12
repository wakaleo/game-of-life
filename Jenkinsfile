node('JDK8'){
	stage('Sourcecode'){
		sh 'git config --global http.version HTTP/1.1' 
		sh 'git clone --branch sprint1-develop --depth 1 https://github.com/DuyDinhhh/game-of-life.git'
	}
	stage('Build the code'){
		sh 'mvn package'
	}
	stage('Archiving and Test Results'){
		junit '**/surefire-reports/*.xml'
		archiveArtifacts artifacts: '**/*.war', followSymlinks: false
	}
}

