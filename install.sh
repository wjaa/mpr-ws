#!/bin/bash
echo "INICIANDO BUILD DO WS"
#pull e build do mpr-ws
git pull && mvn clean install

path=target/ws.jar

if [ -f "$path" ]; then
    echo "Removendo os containers e as imagens anteriores"
    docker rm -f be && docker rmi mpr/ws
    docker rm -f mysql && docker rmi mpr/mysql
    echo "Iniciando o compose"
    docker-compose up -d
    echo "FIM DO BUILD"

else
    echo "ERROR: Algum erro no build não gerou o war. Implementar envio de email"
fi