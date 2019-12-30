
Pipeline CI Tareas
===================

  - [Build](#build)
  - [Unit Tests](#unit-tests)
  - [Sonarqube](#sonarqube)
  - [Quality Gate](#quality-gate)
  - [Build Image](#build-image)
  - [Deploy](#deploy)


  ## Build ##

La tarea consiste en realizar la compilación del Microservicio. En cual se hace la compilación a través de Gradle.

  ## Unit Tests ##

La tarea consiste en la ejecución de las pruebas unitarias del Microservicio y generar los reportes correspondientes.

  ## Sonarqube ##

La tarea consiste en realizar el análisis del código fuente y realizar la publicación en el servidor  Sonarqube de Mallplaza. Además de aplicar las reglas de código establecida en el Quality Gates de Mallplaza.

  ## Quality Gate ##

La tarea consiste en realizar la verificación en el servidor Sonarqube de Mallplaza del estatus del proyecto. Si se encuentra en estado Failed,Passed y si cumple la cobertura de código establecida.

  ## Build Image ##

La tarea consiste en realizar la generación de la imagen docker del Microservicio y realizar la publicación en el container registry de Mallplaza que se encuentra en GCP

  ## Deploy ##

La tarea consiste en el levantamiento del Microservicio en Kubernetes en cluster de GCP o On Premise.




| **Variables de Entorno Gitlab**| **Descripción**|
| ----- | ---- |
|VAULT_ENGINE_NAME|Este campo hace referencia a al nombre del Engine del proyecto en Vault|
| **Desarrollo**| **Ambiente**|
|DESA_CLUSTER_NAME| Nombre de clúster en el cual se hará la instalación de la API. |
|DESA_ZONA_CLUSTER| Zona donde se encuentra ubicado en clúster. |
|DESA_PROJECT_ID| ID del proyecto de desarrollo. |
|DESA_STATUS_SECRETS_REGISTRY| Se inserta el valor correspondiente al status de los secrets 0 (Crear Secrets y Deploy) o 1 Deploy  |
| **Produccion**| **Ambiente**|
|PROD_CLUSTER_NAME| Nombre de clúster en el cual se hará la instalación de la API. |
|PROD_ZONA_CLUSTER| Zona donde se encuentra ubicado el clúster. |
|PROD_PROJECT_ID| ID del proyecto de produccion. |
|PROD_STATUS_SECRETS_REGISTRY| Se inserta el valor correspondiente al status de los secrets 0 (Crear Secrets y Deploy) o 1 Deploy  |