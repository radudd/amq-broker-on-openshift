# About
These two clients represent Quarkus AMQP Clients and are configured with the Red Hat build of QPDID JMS. 
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