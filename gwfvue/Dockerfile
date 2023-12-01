FROM nginx
COPY dist/ /usr/share/nginx/html/
COPY nginx/default.conf /etc/nginx/conf.d/default.conf
COPY nginx/server.crt /etc/server.crt
COPY nginx/server.key /etc/server.key