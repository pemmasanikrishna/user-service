## Simple User rest service using Java

This is a demo application developed in Java 1.8 using
[`jdk.httpserver`](https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/package-summary.html) module.

## Building the project

This is a maven based project and can be built using 

`mvn  clean install`

The above command will produce a jar file in the target directory.

Once the above jar file is produced, the docker image  can be built using the [Dockerfile](Dockerfile)

## Deploying the service

The [helm chart](charts/user-service) can be used to generate the kubernetes manifest and deploy the pod

The service uses the environment variables 

`SERVER_PORT` to override the default port 8080 on which the service listens.
`MAX_REQUESTS_PER_MINUTE` to override the default rate limiting of 10 requests per minute per IP.

