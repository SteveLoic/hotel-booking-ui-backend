FROM node:22 AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --prod

FROM  nginx:alpine	
RUN rm -rf /usr/share/nginx/html/*
COPY --from=build /app/dist/hotel-booking-ui/browser /usr/share/nginx/html
COPY ./nginx.conf  /etc/nginx/conf.d/default.conf
EXPOSE 8080