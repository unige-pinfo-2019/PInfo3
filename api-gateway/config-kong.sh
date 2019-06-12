#Creates the services.
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=classads-service" --data "url=http://classads-service:8080/classads"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=classadsresponse-service" --data "url=http://classadsresponse-service:8080/classadsresponses"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=search-service" --data "url=http://search-service:8080/search"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=web-ui" --data "url=http://web-ui:8081"
#Creates the routes
curl -S -s -i -X POST  --url http://api-gateway:8001/services/classads-service/routes --data "paths[]=/api/classads"
curl -S -s -i -X POST  --url http://api-gateway:8001/services/classadsresponse-service/routes --data "paths[]=/api/classadresponses"
curl -S -s -i -X POST  --url http://api-gateway:8001/services/search-service/routes  --data "paths[]=/api/search"
curl -S -s -i -X POST  --url http://localhost:8001/services/web-ui/routes  --data 'hosts[]=pinfo3.unige.ch'


#Enable the Open ID Plugin
#curl -S -s -i -X POST  --url http://api-gateway:8001/plugins --data "name=jwt" --data "enabled=true"
