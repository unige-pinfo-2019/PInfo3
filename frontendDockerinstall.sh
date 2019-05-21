BASEDIR=$(dirname $(readlink -f "$0"))

## web-ui ---------------------------------------------
cd $BASEDIR/web-ui
docker build --tag=2019pinfo3/web-ui:latest .

