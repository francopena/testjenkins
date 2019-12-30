pipeline {
    agent any 
    stages {
        stage('Build') { 
            agent {
                docker { image 'gradle:alpine' }
            }
            steps {
              sh 'gradle build -x test'
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
