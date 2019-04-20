#!/bin/bash
echo "INICIANDO BUILD DO WS"
echo "PARANDO OS CONTAINERS"
docker stop ws && docker stop nginx && docker stop nginx-nt && docker stop notification

#pull e build do mpr-ws
git pull && mvn clean install

path=target/ws.jar

if [ -f "$path" ]; then
    echo "Removendo os containers e as imagens anteriores"
    docker rm -f ws && docker rmi mpr/ws
    docker rm -f nginx && docker rmi mpr/nginx
    echo "Iniciando o compose"
    docker-compose up -d
    echo "FIM DO BUILD"
    echo "ESPERA!!!! PRECISA SUBIR OS OUTROS CONTAINERS...."
    docker start notification && docker start nginx-nt
    echo "AGORA FECHOU!"

else
    echo "ERROR: Algum erro no build não gerou o war. Implementar envio de email"
    docker start ws && docker start nginx
fi