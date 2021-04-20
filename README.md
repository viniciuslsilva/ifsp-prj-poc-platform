# PRJ - PipeGene Poc

A simple poc to validate an idea of a project for college discipline.  
This project has two services:

- fake-external-service
- platform


## Index
* [Setup](#Setup)
* [Testing-Services](#Testing-Services)


## Setup
### Requirements

* Installed:
  1.	[git](https://www.digitalocean.com/community/tutorials/how-to-contribute-to-open-source-getting-started-with-git)
  2.	[Docker](https://www.docker.com/)

* Optional:
  1.	[Java 11](https://www.oracle.com/technetwork/java/javase/overview/index.html)
  2.	[Maven 3.x](https://maven.apache.org/install.html)
  3.	[Docker-Compose](https://docs.docker.com/compose/install/)

### Run Application
##### Clone source code from git
```  
$  git clone https://github.com/viniciuslsilva/ifsp-prj-poc-platform .  
```

There are two ways to running these applications, the first one is building docker images using Dockerfile and starting the containers. The second one is using the docker-compose file to build and start the containers but needs optional requirements listed above.
To allow the two containers to communicate with each other is necessary to config a network for them. This is not documented yet for the first method of running these services, so I strongly recommend following with the second method because it's encapsulated at docker-compose file.

##### Method 1 - fake-external-service:	Building docker images using Dockerfile
```  
$ docker build -t="fake-external-service" ./fake-external-service/  
```  
##### Run Docker Container with built image
```  
$ docker run -p 5000:5000 -it --rm fake-external-service   
```  

#####  Stop Docker Container:
```  
docker stop `docker container ls | grep "fake-external-service:*" | awk '{ print $1 }'`  
```  
##### Method 1 - platform:	Building docker images using Dockerfile
```  
$ docker build -t="platform" ./platform/  
```  
Maven build will be executed during creation of the docker image.

>Note:if you run this command for first time it will take some time in order to download base image from [DockerHub](https://hub.docker.com/) and install project dependencies.

##### Run Docker Container  with built image
```  
$ docker run -p 8080:8080 --env FAKE_EXTERNAL_SERVICE_HOST=http://fake-external-service --env FAKE_EXTERNAL_SERVICE_PORT=5000 -it --rm platform
```  

#####  Stop Docker Container:
```  
docker stop `docker container ls | grep "platform:*" | awk '{ print $1 }'`  
```  
#### TODO - Create a network for containers

##### Method 2 - both services : Run with docker-compose

Build and start the containers by running

```  
$ docker-compose up -d 
```  
Maven build will be executed during creation of the docker image.

>Note:if you run this command for first time it will take some time in order to download base image from [DockerHub](https://hub.docker.com/) and install project dependencies.

##### Stop Docker Container:
```  
docker-compose down  
```  
## Testing-Services
Some requests and responses examples:

```  

// request => Sending a new file to be process
curl --location --request POST 'localhost:8080/v1/uploads' \
--form 'file=@"/$HOME/CancerGenomicsLib/input/GBM_MEMo.maf"'

// response => File name of processed file available for download
GBM_MEMo_filtered-2021-04-14T02:33:55.436303-maf

// request => Sending a new file to be process and already receive the processed file for download
curl --location --request POST 'localhost:8080/v1/uploads/current' \
--form 'file=@"/$HOME/CancerGenomicsLib/input/GBM_MEMo.maf"'


```  
>Note: You will be to change the file parameter value to a valid path from the input file. There is a valid file example available on this repository at [fake-external-service/app/input/GBM_MEMo.maf](https://github.com/viniciuslsilva/ifsp-prj-poc-platform/blob/main/fake-external-service/input/GBM_MEMo.maf)

**[⬆ Back to Index](#index)**
