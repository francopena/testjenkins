pipeline {
    agent any 
    stages {
        stage('Build') { 
            agent {
                docker { image 'zentadevops/sonar-scanner:3.2.0.1227-prd' }
            }
            steps {
              sh 'Hola1'
            }
        }
        // stage('Test') { 
        //     steps {
        //       sh 'Hola2'
        //     }
        // }
        // stage('Deploy') { 
        //     steps {
        //       sh 'Hola3'
        //     }
        // }
    }
}