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
            post {
               always {
                   archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
                  //junit 'build//jacocoReport/jacocoXML/jacoco.xml'
               }
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
        }
        stage('BuildImage'){
            agent{
              docker { image 'docker.io/google/cloud-sdk' }
            }
            steps {
               sh 'echo hola123'
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
              sh 'echo "ewogICJ0eXBlIjogInNlcnZpY2VfYWNjb3VudCIsCiAgInByb2plY3RfaWQiOiAicHJveWVjdG8tY2FwYXNpdGFjaW9uIiwKICAicHJpdmF0ZV9rZXlfaWQiOiAiYjRkMGQ0Y2Q3MWQ2OTcwZjQwODE5MWY2ZDcxOGUzZWZiNDc3ZmExNSIsCiAgInByaXZhdGVfa2V5IjogIi0tLS0tQkVHSU4gUFJJVkFURSBLRVktLS0tLVxuTUlJRXZnSUJBREFOQmdrcWhraUc5dzBCQVFFRkFBU0NCS2d3Z2dTa0FnRUFBb0lCQVFEQTdiRVk0VkhWcVRuSFxuSzBEek4rbkZJcExDWlpBeDB5bE1kb1JodzMzeUVVa250b29CVlhOemo4ZUhzcDA0eXFQZ0FHSjdQbmFnTWZjRFxuNlJ5Wi9hN0lmODlubGFRcVN2anBYSWVPajNvRW8vNHorYWtFVStoSzRiV0MwN0pPOTZZSFpOS2V6T29iR3AyTVxucldLbEFQQ1hLZFJlNHlJS29GRThhMWlmM2VHTVZHeU1XYzh1OTcrQXRDNFRXNTA1TWhPWDl5SExJd2JCVTNxcVxuSFlyNG51Vzd2b2V6aDU2a3pUQ1VSK0dkd1lWQXI5VjFKR3VocnNEaXlGL0NMV1pTZmFwUGlsS2V3OTdxUUo4SVxubXlOenJWSHV6bnFkWjdYdHhFVTNXZ3Rvc0FaTEFwL3JlbERKU3hKc2RHaXVTNUFKSnBBUndJbnJ1SzI0RXdBZFxuK2djUVlYdWxBZ01CQUFFQ2dnRUFWMTRzb1g1RUwzUmpxR2pUa0hFbEtiT0c3NGJMM214b1d1cWU5NkR1azZVMVxuVWtrdFJQNWIrcHpGZUpPdjlFeDhHdEJCeU5zWUlHS1FJTks3czVhNHhLNXdqOUdwMXNjOTRud1hIVDJuUFp2QVxuWGV3MUdMTWcwbXhWWlFvV1NRR0lTL2ZpRTZ6clpWdlB0aW04cnNsMnFwWDBTUVlsMzljTEFzZ0FDZUdTcHhlM1xuakEzZk8zdldRRVU5M2hMMVFmMnZDSmQ2dmhLd2pIWFUrNktmcHdQc1NvSG9kVUd3bXFyd1Vlb3NrVDc5V2pmaFxuNHdDdWZNMDNObWVhbGhWbEZFaXhIRUExR3FGTE8zZHp4dGV4MHN5RWVWaUpiNGhoVjMzUWFRNHN1VFduMzkxZ1xudEVHaU83QURpSjJEWW5haEV5aUJWa2FQRmsvV0ZwRGtGcTFZNmZWcTN3S0JnUUQ2K3VIM3N6VEE5eU1SWDk1d1xuaDJnNmVoZ1ZhdHVCdjduUk1tNjNCbnNBYzNvZFJQR2pUVy8rOGpkbm1rT0h4Mk55RW5ESjZFUndnemUySldLV1xuVmZ0MDY2SXcvUFAvREg5Y1kvSURCWkhPSzZ1NUhoanJsZWR5Zm05VjBhanBJekNZaTVmalIzUjNHTUo4SEovUVxuWllyRGVBall0eEtVY0lFNDhZb2RxTFhvMXdLQmdRREV5WS91V09pZUxZMHRkVkg1VG8vNUtMZlVFUXhFb0dKdVxuZ0lQSmdlbjRqcU9PN2pZbWtnL2lyOXRsNkZ2NlZRNGdZYXkwa0NnT0VmeUpXdEtPbHFscS9LdTN2Q0ExSGVjNFxuWW5aZ0lhbTJaOFJsWmpuQjdneUVFS1lhRXBTQXBTRVE4SHJPY3hDRnlHK1NBZHB2YlBFckpTZHd0ajBnMWlxbFxubm1Xb3dzS0Q0d0tCZ0JIdUhucWR1T1VZUXM4WTlFRkJ5elp5Um9veEtpRm8yeE9ROE12dWtKY0ZxUEd5WFRqM1xuYlVyZmJ3bmR3WGxQeTFENDlFbllhQnRKcEk5RXVIaFpDQy9nTGFRbVRpQW9lbWpWVXhMWU1RUnpwajhCWVhKalxuTVRmQ3hKMXk3d2hJMVUzd214ZEhvWkJvbW14WGR5dGlOYm1sTjZ5VDE2WDNLNlF2elZnMUYxbmRBb0dCQUpydFxuUlUrVTE2NlFsakZQeURYSzBEYU5CQTBGVHU5SWhiWVJuNitwaEJhWDFkVFZYZlNIMGRIYU9aVEZsb1JES2xmOFxuRGdncDVoOE5zaEJCWEx1OWlhd2RPT1B5VERrRlRlQnFSRi95b0xabWc0eUJLcit2TmxOUytESldwbXVSbGJMTVxuWVNMZ0hBWDNnejhrRG1EakdBdXJ1SjFZSEZBWnpJR1BidUdxVTVHWkFvR0JBTVdHemtLeUtQQ3E5ZTBoalNMcFxuaFg1OHJpNHpldmRnSDhxaFdQZ0ZKUi9lTW0yRVplMWMvVURSNHpLaU5NVXV3d21hWjRiOGVxejFkSjlwVEVWclxuVTR6K29RbEtEUlFZM1duMC90RXQyZC9FWmU5T3dPNFNETFhRRFBMVy9Kb1gvalZraHVUM0F3aGovYjk2Vno1alxubndScy9GMGwrODhsSkNVcDNmMUJXSFhrXG4tLS0tLUVORCBQUklWQVRFIEtFWS0tLS0tXG4iLAogICJjbGllbnRfZW1haWwiOiAidGVycmFmb3JtLWNhcGFjaXRhY2lvbmVzQHByb3llY3RvLWNhcGFzaXRhY2lvbi5pYW0uZ3NlcnZpY2VhY2NvdW50LmNvbSIsCiAgImNsaWVudF9pZCI6ICIxMDk0MTM0MTYzNzI3NjA5Nzc0NDYiLAogICJhdXRoX3VyaSI6ICJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20vby9vYXV0aDIvYXV0aCIsCiAgInRva2VuX3VyaSI6ICJodHRwczovL29hdXRoMi5nb29nbGVhcGlzLmNvbS90b2tlbiIsCiAgImF1dGhfcHJvdmlkZXJfeDUwOV9jZXJ0X3VybCI6ICJodHRwczovL3d3dy5nb29nbGVhcGlzLmNvbS9vYXV0aDIvdjEvY2VydHMiLAogICJjbGllbnRfeDUwOV9jZXJ0X3VybCI6ICJodHRwczovL3d3dy5nb29nbGVhcGlzLmNvbS9yb2JvdC92MS9tZXRhZGF0YS94NTA5L3RlcnJhZm9ybS1jYXBhY2l0YWNpb25lcyU0MHByb3llY3RvLWNhcGFzaXRhY2lvbi5pYW0uZ3NlcnZpY2VhY2NvdW50LmNvbSIKfQo=" | base64 -d > auth.json'
              sh 'gcloud config set project "proyecto-capasitacion"'
              sh 'gcloud auth activate-service-account terraform-capacitaciones@proyecto-capasitacion.iam.gserviceaccount.com --key-file=auth.json --project=proyecto-capasitacion'
              sh 'gcloud container clusters get-credentials  cluster-deploy --zone us-east1 --project proyecto-capasitacion' 
              sh 'kubectl --namespace default apply -f ./deploy/k8s/deployment.yaml'
              sh 'kubectl --namespace default apply -f ./deploy/k8s/services.yaml'
            }
        }
    }
}
