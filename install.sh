#!/bin/bash
echo "INICIANDO BUILD DO WS"
#pull e build do mpr-ws
git pull && mvn clean install -DskipTests=true

path=target/ws.jar

if [ -f "$path" ]; then
    echo "Removendo os containers e as imagens anteriores"
    docker rm -f ws && docker rmi mpr/ws
    docker rm -f nginx && docker rmi mpr/nginx
    echo "Iniciando o compose"
    docker-compose up -d
    echo "FIM DO BUILD"

else
    echo "ERROR: Algum erro no build não gerou o war. Implementar envio de email"
fi