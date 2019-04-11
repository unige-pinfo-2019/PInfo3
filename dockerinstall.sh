#!/bin/sh
###script to locally install docker files
## classads
ORIGINDIR=$(pwd)
BASEDIR=$(dirname $(readlink -f "$0"))
cd $BASEDIR/classads
mvn package
docker build --tag=classads .
## frontend
cd $BASEDIR/frontend
npm run build
docker build --tag=frontend .
cd $ORIGINDIR
