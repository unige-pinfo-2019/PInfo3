#!/bin/sh
curl -o docker-compose-api-gw.yml https://raw.githubusercontent.com/unige-pinfo-2019/PInfo3/master/docker-compose/docker-compose-api-gw.yml
curl -o docker-compose-unitrade.yml https://raw.githubusercontent.com/unige-pinfo-2019/PInfo3/master/docker-compose/docker-compose-unitrade.yml
docker-compose -f docker-compose-unitrade.yml pull
docker-compose -f docker-compose-api-gw.yml pull
docker-compose -f docker-compose-unitrade.yml up -d
docker-compose -f docker-compose-api-gw.yml up -d
exit
