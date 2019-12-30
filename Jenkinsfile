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
        stage('BuildImage'){
            agent{
              docker { 
                  image 'docker.io/google/cloud-sdk'
                  args '-v /var/run/docker.sock:/var/run/docker.sock' +
                     '--user=root' +
                     '--cap-drop=ALL' +
                     '--cap-add=DAC_OVERRIDE'
                     }
            }
            steps {
              sh 'docker build -t trybuild:V1.0 .'
            }
        }
        stage('SonnarQube'){
            agent{
              docker { image 'zentadevops/sonar-scanner:3.2.0.1227-prd'}
            }
            steps {
              sh 'sonar-scanner -Dsonar.host.url=$SONARQUBE_HOST -Dsonar.sources=. -Dsonar.java.binaries=. -Dsonar.login=$SONARQUBE_TOKEN  -Dsonar.projectKey=$JOB_NAME:$GIT_BRANCH -Dsonar.projectName=$JOB_NAME:$GIT_BRANCH -Dsonar.coverage.jacoco.xmlReportsPath=build/jacocoReport/jacocoXML/jacoco.xml -Dsonar.jacoco.reportPaths=build/jacoco/test.exec -Dsonar.junit.reportPath=build/test-results/test/** -Dsonar.language=java -Dsonar.java.test.binaries=build/classes/** -Dsonar.jacoco.itReportPath=build/jacoco/test.exec -Dsonar.java.coveragePlugin=jacoco -Dsonar.exclusions=**/*.js,**/*.css,**/*.html,src/test/**,src/main/java/cl/mallplaza/salesforcemanager/dto/**,src/main/java/cl/mallplaza/salesforcemanager/exception/** -Dsonar.coverage.exclusions=src/test/**,src/main/java/cl/mallplaza/salesforcemanager/configuration/**,src/main/java/cl/mallplaza/salesforcemanager/SalesforceManagerUtilApplication**,src/main/java/cl/mallplaza/salesforcemanager/dto/**,src/main/java/cl/mallplaza/salesforcemanager/exception/**,src/main/java/cl/mallplaza/salesforcemanager/util/**'
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
