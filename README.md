# Welcome to ByteTheDust project repository

We are a 6 members team :

 - Aurélia Autem (Project leader)
 - Gibran Chevalley
 - Attila Nacsa (System Manager)
 - Guy-Raphaël Stauffer
 - Pavlos Tserevelakis
 - David alexander


## Commands

Open a terminal in the classads-service directory, from here you can execute the following commands to start a backend server locally at the address `localhost:8080` :
```
mvn clean install
mvn thorntail:run
```

Open a terminal in the frontend directory, from here you can execute the following command to start a frontend server locally at the address `localhost:8081` :
```
npm run dev
```

### Skip tests
It my take some time to build because of the tests, so if you want to skip them, we can use `-DskipTests` like :
```
mvn clean install -DskipTests
mvn thorntail:run -DskipTests
```


### Firefox REST client (to run only backend)

If you want to run some backend without running frontend, you can install the firefox extension RESTED.
Then, you run the backend with `mvn thorntail:run` which will start a local server at `localhost:8080`.
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
