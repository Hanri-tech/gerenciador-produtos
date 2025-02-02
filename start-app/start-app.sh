#!/bin/sh

# Aguardando o SQL Server estar pronto na porta 1433
echo "Aguardando SQL Server..."
until nc -z -v -w30 sqlserver 1433
do
  echo "Aguardando SQL Server estar ouvindo na porta 1433..."
  sleep 13
done

# Quando o SQL Server estiver ouvindo, inicia a aplicação
echo "SQL Server está ouvindo na porta 1433. Iniciando aplicação..."
exec java -jar /app/gerenciadorproduto-1.0.jar
