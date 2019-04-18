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
mkdir java java/domain java/api resources resources/META-INF webapp
touch java/domain/.gitkeep java/api/.gitkeep resources/META-INF/persistence.xml

cd ../test
mkdir java java/domain java/api resources resources/META-INF
touch java/domain/.gitkeep java/api/.gitkeep resources/META-INF/persistence.xml
