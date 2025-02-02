FROM openjdk:17-jdk-oraclelinux8

# Instalar netcat usando microdnf
RUN microdnf install -y nc

# Copia o script start-app.sh para dentro do container
COPY ./start-app/start-app.sh /start-app.sh
RUN chmod +x /start-app.sh


WORKDIR /app

# Copiar o arquivo JAR gerado pelo Gradle
COPY build/libs/gerenciadorproduto-1.0.jar /app/gerenciadorproduto-1.0.jar

EXPOSE 8080


# Executa o script de iniciar o spring boot
ENTRYPOINT ["/start-app.sh"]