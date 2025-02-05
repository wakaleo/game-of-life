pipeline {
    agent { label 'node1' }
    triggers { pollSCM('* * * * *') }
    
    // Remove global JDK 8; set JDK per stage instead
    parameters {
        string(name: 'mvn_goal', defaultValue: 'package', description: 'package build')
    }
    environment {
        SONAR_TOKEN = credentials('SONAR_CLOUD')
    }
    
    stages {
        stage('VCS') {
            steps {
                git url: 'https://github.com/Danish-Ansarii/game-of-life.git', branch: 'declerative'
            }
        }
        
        stage('Build') {
            tools { jdk 'jdk_8' }  // Use Java 8 for building the app
            steps {
                sh "mvn ${params.mvn_goal}"
            }
        }
        
        stage('SonarCloud Analysis') {
            tools { jdk 'jdk_17' }  // Switch to Java 17 for Sonar
            steps {
                withSonarQubeEnv('SONAR_CLOUD') {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=gameoflife \
                        -Dsonar.organization=danish-ansarii \
                        -Dsonar.host.url=https://sonarcloud.io \
                        -Dsonar.login=$SONAR_TOKEN                    
                    """
                }
            }
        }
        
        // Keep other stages unchanged
        stage('Packages and Tests Reports') {
            steps {
                archiveArtifacts artifacts: '**/target/gameoflife.war', followSymlinks: false
                junit '**/target/surefire-reports/TEST-*.xml'
            }
        }
        
        stage('Quality Gate') {
            steps {
                script {
                    timeout(time: 5, unit: 'MINUTES') {
                        waitForQualityGate abortPipeline: true
                    }
                }
            }
        }
    }
}