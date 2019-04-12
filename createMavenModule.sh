#!/bin/sh

# Creating the folder
folderName=$1
mkdir $folderName

cd $folderName
mkdir src
touch pom.xml Dockerfile

cd src/
mkdir main test

cd main/
mkdir java resources resources/META-INF webapp
touch resources/META-INF/persistence.xml

cd ../test
mkdir java resources resources/META-INF
touch resources/META-INF/persistence.xml
