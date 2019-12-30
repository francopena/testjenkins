# salesforce manager util

| **SonarQube**| **Bugs**| **Vulnerabilities**| **Coverage**| **Quality Gate Status**|
| ----- | ---- | ----- | ---- | ----- |
| Master |[![Bugs](https://sonar.mallplaza.com/api/project_badges/measure?project=api-proxy%3Amaster&metric=bugs)](https://sonar.mallplaza.com/dashboard?id=api-proxy%3Amaster)|[![Vulnerabilities](https://sonar.mallplaza.com/api/project_badges/measure?project=api-proxy%3Amaster&metric=vulnerabilities)](https://sonar.mallplaza.com/dashboard?id=api-proxy%3Amaster)|[![Coverage](https://sonar.mallplaza.com/api/project_badges/measure?project=api-proxy%3Amaster&metric=coverage)](https://sonar.mallplaza.com/dashboard?id=api-proxy%3Amaster)|[![Quality Gate Status](https://sonar.mallplaza.com/api/project_badges/measure?project=api-proxy%3Amaster&metric=alert_status)](https://sonar.mallplaza.com/dashboard?id=api-proxy%3Amaster)|
| Develop |[![Bugs](https://sonar.mallplaza.com/api/project_badges/measure?project=api-proxy%3Adevelop&metric=bugs)](https://sonar.mallplaza.com/dashboard?id=api-proxy%3Adevelop)|[![Vulnerabilities](https://sonar.mallplaza.com/api/project_badges/measure?project=api-proxy%3Adevelop&metric=vulnerabilities)](https://sonar.mallplaza.com/dashboard?id=api-proxy%3Adevelop)|[![Coverage](https://sonar.mallplaza.com/api/project_badges/measure?project=api-proxy%3Adevelop&metric=coverage)](https://sonar.mallplaza.com/dashboard?id=api-proxy%3Adevelop)|[![Quality Gate Status](https://sonar.mallplaza.com/api/project_badges/measure?project=api-proxy%3Adevelop&metric=alert_status)](https://sonar.mallplaza.com/dashboard?id=api-proxy%3Adevelop)|
| Features|[![Bugs](https://sonar.mallplaza.com/api/project_badges/measure?project=api-proxy%3Afeatures&metric=bugs)](https://sonar.mallplaza.com/dashboard?id=api-proxy%3Afeatures)|[![Vulnerabilities](https://sonar.mallplaza.com/api/project_badges/measure?project=api-proxy%3Afeatures&metric=vulnerabilities)](https://sonar.mallplaza.com/dashboard?id=api-proxy%3Afeatures)|[![Coverage](https://sonar.mallplaza.com/api/project_badges/measure?project=api-proxy%3Afeatures&metric=coverage)](https://sonar.mallplaza.com/dashboard?id=api-proxy%3features)|[![Quality Gate Status](https://sonar.mallplaza.com/api/project_badges/measure?project=api-proxy%3Afeatures&metric=alert_status)](https://sonar.mallplaza.com/dashboard?id=api-proxy%3Afeatures)|

- [**Gitlab CI**](gitlab-ci-info.md) 

> Application that allows you to send api salesforce messages from Google Cloud Platform to salesforce (Mail)...

[INSERTAR DESCRIPCION]

## Configuration

Through the application configuration file ([application.yaml] (./ src / main / resources / application.yaml)) You must define the parameters so that you can connect to the salesforce service and send the mail.

```yaml
properties:
  authorization:
    request:
      token: 'MANAGER-AUTH-TOKEN'
  salesforce:
    mail:
      send:
        url: 'SALESFORCE-MAIL-URL'
    token:
      url: 'SALESFORCE-TOKEN-URL'
      request:
        grant-type: 'SALESFORCE-TOKEN-CLIENT-CREDENTIALS'
        client-id: 'SALESFORCE-TOKEN-CLIENT-ID'
        client-secret: 'SALESFORCE-TOKEN-CLIENT-SECRET'
        scope: 'SALESFORCE-TOKEN-SCOPE'
        account-id: 'SALESFORCE-TOKEN-ACCOUNT_ID'
```

Environment variables.

```yaml
properties:
  authorization:
    request:
      token: '${MANAGER_AUTH_TOKEN}'
  salesforce:
    mail:
      send:
        url: '${SALESFORCE_MAIL_URL}'
    token:
      url: '${SALESFORCE_TOKEN_URL}'
      request:
        grant-type: '${SALESFORCE_TOKEN_CLIENT_CREDENTIALS}'
        client-id: '${SALESFORCE_TOKEN_CLIENT_ID}'
        client-secret: '${SALESFORCE_TOKEN_CLIENT_SECRET}'
        scope: '${SALESFORCE_TOKEN_SCOPE}'
        account-id: '${SALESFORCE_TOKEN_ACCOUNT_ID}'
```

## At the beginning

Within the application startup process, verify the connection to the configured service account, if there is a configuration failure, 
the application will not start.

## How to start

Next, you must configure the file ([application.yml] (./ src / main / resources / application.yml)) that indicates the connection parameters for Salesforce. After that, you can test the application with the following command.

```bash
./gradlew clean bootRun
```

And the application shall start.


Finally, use the supplied `Dockerfile` to create the container and run the application.