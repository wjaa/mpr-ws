#!/bin/bash
echo "INICIANDO BUILD DO WS"
#pull e build do mpr-ws
git pull && mvn clean install

path=target/ws.jar

if [ -f "$path" ]; then
    echo "Removendo o container e a imagem anterior"
    docker rmi mpr/ws && sudo docker rm -f mpr-ws_be_1
    echo "Gerando nova imagem"
    docker build -t mpr/ws .
    echo "Gerando container"
    docker-compose up -d
    echo "FIM DO BUILD"

else
    echo "ERROR: Algum erro no build não gerou o war. Implementar envio de email"
fi