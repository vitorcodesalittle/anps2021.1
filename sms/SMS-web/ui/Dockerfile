FROM node:fermium as build
WORKDIR /app
COPY package.json package-lock.json ./
RUN ls -al 
RUN npm install
COPY . . 
RUN npm run build

FROM nginx
COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
