FROM node:fermium
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . . 
EXPOSE 1234
CMD ["npm", "start"]
