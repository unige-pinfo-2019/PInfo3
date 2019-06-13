#!/bin/bash

docker-compose -f docker-compose/docker-compose-unitrade.yml down
docker-compose -f docker-compose/docker-compose-search.yml down

docker-compose -f docker-compose/docker-compose-search.yml up & 
gnome-terminal -x docker-compose -f docker-compose/docker-compose-unitrade.yml up 

sleep 50
cd web-ui
npm run dev   






