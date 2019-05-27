# frontend

## Commands for keycloak

To list container ids :

`docker ps`

To open a bash in keycloak container :

`docker exec -it <keycloak_id> bash`

To copy files :

```
docker cp f454cd45bac7:/opt/jboss/keycloak/standalone/configuration/standalone.xml ./Documents/Project/PInfo3/docker-compose/iam-config/standalone.xml

docker cp ./Documents/Project/PInfo3/docker-compose/iam-config/theme.properties f454cd45bac7:/opt/jboss/keycloak/themes/pinfo3/login/theme.properties
```



## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Run your tests
```
npm run test
```

### Lints and fixes files
```
npm run lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).
