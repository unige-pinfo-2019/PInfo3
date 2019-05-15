# Commandes

## Pour lancer les microservices

Pour créer les images docker avec maven :

```
mvn clean install
mvn install -Ppackage-docker-image

```

Pour créer seulement certaines images (par exemple celle de classads-service) : `mvn install -Ppackage-docker-image -pl=.,classads-service`.

Pour éviter de faire les tests : `-DskipTests`.

On peut vérifier que les images ont été crées : (2019pinfo3/user-service et 2019pinfo3/classads-service)

```
docker image ls  
```

Ensuite :

```
docker-compose -f docker-compose/docker-compose-search.yml down
docker-compose -f docker-compose/docker-compose-unitrade.yml down
```

Ensuite, dans des terminaux différents :

```
docker-compose -f docker-compose/docker-compose-search.yml up
```

```
docker-compose -f docker-compose/docker-compose-unitrade.yml up
```

Pour savoir sur quel port les service tournent, voir le docker-compose-unitrade.yml.
(En localhost :
  * 8081 : classads-service
  * 8082 : user-service
  * 8083 : web-ui
  * 8084 : search-service)

Pour détacher les microservices (pas d'arrêt dans le terminal) : rajouter `-d`.

## Problèmes

### Permission denied sur docker.sock

```
sudo chmod 776 /var/run/docker.sock
```

Si toujours impossible de d'exécuter docker sans sudo, essayer :

```
sudo groupadd docker
sudo useradd -G docker $USER
sudo service docker restart
```

### Maven cannot delete some files ou pour delocker les dossiers target

Il faut se placer dans le dossier parent, puis supprimer les dossier target :

`sudo rm -Irv ./*/target/`

### Changement de modèle dans kafka

Lancer le docker-compose-unitrade.yml, ouvrir un terminal une fois kafka lancé :

Copie l'id du container de kafka graĉe à `docker ps`.

```
docker exec -t -i 8b797c0d80b3 /bin/bash
cd var/lib/kafka
rm -r data/*
```

Ensuite, kafka va planter, il faut refaire `down` puis `up` pour docker-compose-unitrade.yml.
