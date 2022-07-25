pipeline {
    agent any
    environment {
		DOCKERHUB_CREDENTIALS=credentials('DOCKERHUB_LOGIN')
	}
      stages {
            stage('Build') {
            agent { label 'DOCKER' }
            steps('Docker build') {
                
                sh 'docker build --no-cache -t rajanikanthraju/gol_dev:v1.${BUILD_ID} .'
                                  }
              }
            stage('DockerHub Login') {
            agent { label 'DOCKER' }
			steps {
				sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
			      }
		       }
          stage('Image Push') {
          agent { label 'DOCKER' }
			steps {
				sh 'docker push rajanikanthraju/gol_dev:v1.${BUILD_ID}'
			}
		}
       stage('Deploying image to K8s cluster') {
          steps {
				sh 'kubectl apply -f gol_svc.yml'
			}
		}
      }
    }
