pipeline {
agent any 
stages {
   stage ('build'){
def mvnHome = tool 'Apache Maven 3.3.9'
sh " '${mvnHome}/bin/mvn' compile"
                  }
       }
}
