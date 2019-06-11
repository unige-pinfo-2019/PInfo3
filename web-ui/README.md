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

To access keycloak admin console

`localhost:8080/auth/admin/master/console`


## Keycloak url paths

** (Attention à bien remplacer les host et port ) **

** Le champ <redirect_uri> est une propriété accessible de keycloak **

To access user registration page :

`http://<host>:<port>/auth/realms/apigw/protocol/openid-connect/registrations?client_id=web-sso&response_type=code&scope=openid%20email&redirect_uri=<redirect_uri>`

To access user login page :

`http://<host>:<port>/auth/realms/apigw/protocol/openid-connect/auth?client_id=web-sso&response_type=code&scope=openid%20email&redirect_uri=<redirect_uri>`

To logout a user :

`http://<host>:<port>/auth/realms/apigw/protocol/openid-connect/logout?client_id=web-sso&response_type=code&scope=openid%20email&redirect_uri=<redirect_uri>`


To access user infos : (si le user n'est pas connecté, ça renverra sur la page de login)

`\auth\realms\apigw\account`



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
