echo "Aguardando o SQL Server estar pronto..."
/tmp/wait-for-it.sh sqlserver:1433 --timeout=180 -- echo "SQL Server está pronto para conexões"

echo "Criando banco PRODUTODB..."
/opt/mssql-tools/bin/sqlcmd -S sqlserver -U sa -P senha@12345 -d master -i /tmp/create-table.sql

echo "Banco PRODUTODB criado com Sucesso..."