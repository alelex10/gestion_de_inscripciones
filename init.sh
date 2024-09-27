#!/bin/sh

# Iniciar el servicio de MySQL en segundo plano
mysqld_safe --skip-grant-tables &

# Esperar a que MySQL inicie completamente
sleep 10

# Crear la base de datos 'squad_db' y configurar el usuario
mysql -u root <<-EOSQL
    CREATE DATABASE IF NOT EXISTS squad_db;
    CREATE USER IF NOT EXISTS 'usuario'@'%' IDENTIFIED BY 'usuariopassword';
    GRANT ALL PRIVILEGES ON squad.* TO 'usuario'@'%';
    FLUSH PRIVILEGES;
EOSQL

# Iniciar la aplicación
java -jar /app/app.jar
