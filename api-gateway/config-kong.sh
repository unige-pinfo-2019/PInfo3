#Creates the services.
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=classads-service" --data "url=http://classads-service:8080/classads"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=user-service" --data "url=http://user-service:8080/user"
curl -S -s -i -X POST --url http://api-gateway:8001/services --data "name=web-ui" --data "url=http://web-ui:8081"
#Creates the routes
curl -S -s -i -X POST  --url http://api-gateway:8001/services/classads-service/routes --data "paths[]=/api/classads"
curl -S -s -i -X POST  --url http://api-gateway:8001/services/instrument-service/routes   --data "paths[]=/api/user"
curl -S -s -i -X POST  --url http://api-gateway:8001/services/web-ui/routes --data "paths[]=/"
#Enable the Open ID Plugin
curl -S -s -i -X POST  --url http://api-gateway:8001/plugins --data "name=jwt" --data "enabled=false"
