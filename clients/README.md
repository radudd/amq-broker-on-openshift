# About
These two clients represent Quarkus AMQP Clients and are configured with the Red Hat build of QPDID JMS. They are configured to use TLS secured connection.

On server-side the certificates are generated with cert-manager. The configuration of the client-url (failover-url) require the truststore in jks format. Whether create it by converting the ca used to generate the broker certiticates, or use cert-utils operator to automatically enhance the secret containing the pem truststore to add also the jks version of it. After, just mount the truststore containting the jks to the client. With quarkus this can be easily achieved with the quarkus-openshift plugin integration.
If you wish to use the clients without TLS, just replace the `application.properties` file with `aplication.properties.notls` one (located in the same folder).

The producer produces an infinite sequence of numbers consecutively starting with 1. 

# How

Check the `application.properties` of both clients and configure properly the broker urls. Then login to OpenShift and move to the namespace you wish to deploy.

To deploy to OpenShift, just use the Quarkus CLI.

```
cd amqp-producer-qpid-jms 
quarkus build -Dquarkus.openshift.deploy=true -DskipTests=True 
cd ..
```

```
cd amqp-consumer-qpid-jms 
quarkus build -Dquarkus.openshift.deploy=true -DskipTests=True 
cd ..
```