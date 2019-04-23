FROM node:11-alpine
COPY  . /web-ui/
WORKDIR /web-ui
EXPOSE 8081
ENV Name World
RUN npm install
ENTRYPOINT ["npm","run", "dev"]
