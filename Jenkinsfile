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
            agent {
                docker { image 'gradle:alpine' }
            }
            steps {
              sh 'gradle test'
              sh 'gradle jacocoTestReport'
            }
            post {
               always {
                   archiveArtifacts artifacts: 'build/jacocoReport/jacocoHTML/*', fingerprint: true
                  //junit 'build//jacocoReport/jacocoXML/jacoco.xml'
               }
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
