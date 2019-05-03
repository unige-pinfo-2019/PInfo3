
Pour créer les images docker avec maven :

```
sudo mvn clean install
sudo mvn install -Ppackage-docker-image
```

On peut vérifier que les images ont été crées : (2019pinfo3/user-service et 2019pinfo3/classads-service)

```
sudo docker image ls  
```

Ensuite, on lance le docker-compose

```
sudo docker-compose -f docker-compose/docker-compose-unitrade.yml down
sudo docker-compose -f docker-compose/docker-compose-unitrade.yml up

```
