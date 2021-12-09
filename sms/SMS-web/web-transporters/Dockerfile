FROM node:fermium
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . . 
EXPOSE 9001
CMD ["npm", "start"]
