
Pour régler le problème de Permission denied sur docker.sock

```
sudo chmod 776 /var/run/docker.sock
```

Pour exécuter docker sans sudo :

```
sudo groupadd docker
sudo useradd -G docker $USER
sudo service docker restart
```


Pour créer les images docker avec maven :

```

mvn clean install
mvn install -Ppackage-docker-image

```

On peut vérifier que les images ont été crées : (2019pinfo3/user-service et 2019pinfo3/classads-service)

```
sudo docker image ls  
```

Ensuite, on lance le docker-compose

```

sudo docker-compose -f docker-compose/docker-compose-search.yml down
sudo docker-compose -f docker-compose/docker-compose-unitrade.yml down

sudo docker-compose -f docker-compose/docker-compose-search.yml up
sudo docker-compose -f docker-compose/docker-compose-unitrade.yml up

```
