FROM node:19-alpine3.16
WORKDIR /usr/app/
COPY ["package*.json", "."]
RUN npm install
COPY . .
RUN npm install -g @angular/cli
CMD ["ng", "serve", "--host", "0.0.0.0"]
