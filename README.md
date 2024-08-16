# DISCLAIMER
**This is an example repository on how to deploy AMQ Broker in OpenShift using ArgoCD. This is under no circumstances a production grade deployment, it is only meant to get started with AMQ Broker deployment on OpenShift.** 
Please note that with this deployment:
* **AMQ Broker user passwords are stored in blank into this repository**
* **ArgoCD service account will get cluster-admin permissions**

** Use this repo at your own risk** 

# About
This repository contains the manifests to deploy a multi-broker setup of AMQ Broker on OpenShift in a cluster or federated mode. It contains as well ArgoCD Application manifests that deploy AMQ Broker operator and a configured AMQ Broker cluster with two brokers and a set of users.
Additionally it opens NodePorts for connecting clients externally using OpenWire and AMQP protocols.

# Bootstrap

Before you bootstrap your installation, choose the type of multi-cluster setup you want to deploy: federated or clustered.

```
cat bootstrap/amq-broker-dev-cluster.yaml
```

```yaml
apiVersion: argoproj.io/v1alpha1
kind: Application
metadata:
  name: amq-broker-dev-cluster
  namespace: openshift-gitops
spec:
  ... 
  source:
    # Choose this for federated setup
    path: manifests/broker/federation
    # Choose this for clustered setup
    #path: manifests/broker/cluster
    ...
```


Once ArgoCD is installed in your OpenShift cluster, bootstrap the installation of ArgoCD applications

```
oc apply -f bootstrap
```


## [IMPORTANT]  

Configure ActiveMQ Broker as per requiremetns, i.e. address settings, acceptors, persistence, clustering, etc. Check [Red Hat Official Docs](https://access.redhat.com/documentation/en-us/red_hat_amq_broker/7.12/html/deploying_amq_broker_on_openshift/assembly-br-configuring-operator-based-deployments_broker-ocp#assembly-br-configuring-security-operator_broker-ocp) 
