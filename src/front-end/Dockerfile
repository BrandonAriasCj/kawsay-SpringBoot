# Etapa 1: Construcción de la app con Vite
FROM node:20-alpine AS build

WORKDIR /app
COPY . .

# Instala dependencias y construye la app
RUN npm install && npm run build

# Etapa 2: Servir con NGINX
FROM nginx:alpine

# Limpia contenido por defecto de NGINX
RUN rm -rf /usr/share/nginx/html/*

# Copia el contenido construido por Vite
COPY --from=build /app/dist /usr/share/nginx/html

# Exponer el puerto que NGINX usa
EXPOSE 80

# Inicia NGINX en modo foreground
CMD ["nginx", "-g", "daemon off;"]
