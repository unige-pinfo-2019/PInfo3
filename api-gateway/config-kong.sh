#Creates the services.
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=classads-service" --data "url=http://classads-service:8080/classads"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=user-service" --data "url=http://user-service:8080/user"
#Creates the routes
curl -S -s -i -X POST  --url http://api-gateway:8001/services/classads-service/routes --data "paths[]=/api/classads" 
curl -S -s -i -X POST  --url http://api-gateway:8001/services/instrument-service/routes   --data "paths[]=/api/user" 
#Enable the Open ID Plugin
curl -S -s -i -X POST  --url http://api-gateway:8001/plugins --data "name=jwt" --data "enabled=true"
