#!/bin/sh
###script to locally install docker files
## classads-service
ORIGINDIR=$(pwd)
BASEDIR=$(dirname $(readlink -f "$0"))
cd $BASEDIR/classads-service
mvn package
docker build --tag=classads-service .
## frontend
cd $BASEDIR/frontend
npm run build
docker build --tag=frontend .
cd $ORIGINDIR
