FROM node:11-alpine
COPY  . /frontend/
WORKDIR /frontend
EXPOSE 8081
ENV Name World
CMD ["npm", "rebuild", "node-sass"]
CMD ["npm", "install", "dependencies"]
ENTRYPOINT ["npm","run", "dev"]
