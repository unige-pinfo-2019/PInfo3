#!/bin/sh
###script to locally install docker files
ORIGINDIR=$(pwd)
BASEDIR=$(dirname $(readlink -f "$0"))
## classads-service -----------------------------------
cd $BASEDIR/classads-service
mvn package
docker build --tag=2019pinfo3/classads-service:latest .
## web-ui ---------------------------------------------
cd $BASEDIR/web-ui
npm rebuild node-sass
npm install dependencies
npm run build
docker build --tag=2019pinfo3/web-ui:latest .
## api gateway ----------------------------------------
cd $BASEDIR/api-gateway
docker build --tag=2019pinfo3/api-gateway:latest .
## search-service -------------------------------------
cd $BASEDIR/search-service
mvn package
docker build --tag=2019pinfo3/search-service:latest .
## user-service ---------------------------------------
cd $BASEDIR/user-service
mvn package
docker build --tag=2019pinfo3/user-service:latest .


## ending
cd $ORIGINDIR
