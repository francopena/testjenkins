pipeline {
    agent any 
    stages {
        stage('Build') { 
            agent {
                docker { image 'zentadevops/sonar-scanner:3.2.0.1227-prd' }
            }
            steps {
              sh 'echo Hola1'
              sh 'echo Nuevos Cambios'
            }
        }
        stage('Test') { 
            steps {
              sh 'echo Hola2'
            }
        }
        stage('Deploy') { 
            steps {
              sh 'echo cambios raros'
              sh 'echo Hola3'
            }
        }
    }
}
