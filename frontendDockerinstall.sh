BASEDIR=$(dirname $(readlink -f "$0"))

## web-ui ---------------------------------------------
cd $BASEDIR/web-ui
npm rebuild node-sass
npm install dependencies
npm run build
docker build --tag=2019pinfo3/web-ui:latest .

