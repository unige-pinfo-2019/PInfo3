[![Build Status](https://travis-ci.org/unige-pinfo-2019/PInfo3.svg?branch=master)](https://travis-ci.org/unige-pinfo-2019/PInfo3)
[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=unige-pinfo-2019_PInfo3&metric=alert_status)](https://sonarcloud.io/dashboard?id=unige-pinfo-2019_PInfo3)
[![Coverage (Sonar)](https://sonarcloud.io/api/project_badges/measure?project=unige-pinfo-2019_PInfo3&metric=coverage)](https://sonarcloud.io/dashboard?id=unige-pinfo-2019_PInfo3)
[![Bugs (Sonar)](https://sonarcloud.io/api/project_badges/measure?project=unige-pinfo-2019_PInfo3&metric=bugs)](https://sonarcloud.io/dashboard?id=unige-pinfo-2019_PInfo3)
[![Code smells (Sonar)](https://sonarcloud.io/api/project_badges/measure?project=unige-pinfo-2019_PInfo3&metric=code_smells)](https://sonarcloud.io/dashboard?id=unige-pinfo-2019_PInfo3)
[![Vulnerabilities (Sonar)](https://sonarcloud.io/api/project_badges/measure?project=unige-pinfo-2019_PInfo3&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=unige-pinfo-2019_PInfo3)


# Welcome to ByteTheDust project repository

We are a 6 members team :

 - Aurélia Autem (Project leader)
 - Gibran Chevalley
 - Attila Nacsa (System Manager)
 - Guy-Raphaël Stauffer
 - Pavlos Tserevelakis
 - David alexander


## Commands

### Running on the server

You can access the application on `pinfo3.unige.ch`

If you want to deploy the docker images, we need to connect to the VM (with your university login):

```
ssh username@pinfo3.unige.ch
```

Then to execute the following command to update the docker images :

```
docker stop $(docker ls -a -q)
docker rm $(docker ls -a -q)
docker-compose -f docker-compose-unitrade.yml pull
```

Finally, to run

`docker-compose -f docker-compose-unitrade.yml up` : start the containers and display informations (but you won't be able to log out from the VM)

`docker-compose -f docker-compose-unitrade.yml up - d` : just start the containers in detached mode so we can log out from the VM


### Running frontend locally

Open a terminal in the `web-ui` directory, from here you can execute the following command to start a frontend server locally at the address `localhost:8081` :
```
npm run dev
```

### Running backend locally

Open a terminal in a microservice folder, from here you can execute the following commands to start a backend server locally at the address `localhost:8080` :
```
mvn clean install
mvn thorntail:run
```

We can skip tests by using `-DskipTests`.

When Thorntail is ready, open a firefox browser and open a RESTED TAB (by clicking on </>).
There, you can do REST requests.

For example, to post a new ad using a JSON format:
```
Request:
POST --> http://localhost:8080/ClassAd/new

Headers
Content-Type --> application/json

Request body
Type --> JSON
titre --> "Any title"
description --> "Any description"
prix --> 10
```

Now, you can send the request.

## Group infos

Presentation dates:

Type | Date | Présentateurs
------------ | ------------- | -------------
Intermediate presentation | 12.04 | Aurélia + Gibran
Intermediate presentation | 17.05 | Attila + Guy-Raphaël
Final presentation | 07.06 | Aurélia + David + Pavlos
