FROM node:11-alpine
COPY  . /web-ui/
WORKDIR /web-ui
EXPOSE 8081
ENV Name World
CMD ["npm", "rebuild", "node-sass"]
CMD ["npm", "install", "dependencies"]
CMD ["npm", "install", "--save", "/frontend/npm"]
CMD ["npm", "install", "--save", "/web-ui/npm"]
ENTRYPOINT ["npm","run", "dev"]
