FROM frolvlad/alpine-oraclejdk8:slim
MAINTAINER Wagner Jeronimo <ti.wagnerj@braspress.com>
VOLUME /tmp
ADD target/ws.jar ws.jar
RUN sh -c 'touch /ws.jar'
RUN mkdir -p /var/www/static/mpr
ENV JAVA_OPTS="-Duser.timezone=America/Sao_Paulo -Xms128m -Xmx256m"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -Dspring.profiles.active=$profile -Djava.security.egd=file:/dev/./urandom -jar /ws.jar" ]
