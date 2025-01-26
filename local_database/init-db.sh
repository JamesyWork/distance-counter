#!/bin/bash

DB_NAME="app_db"

# Check if the database exists
RESULT=$(mysql -uroot -p"$MYSQL_ROOT_PASSWORD" -e "SHOW DATABASES LIKE '$DB_NAME';")

if [ -z "$RESULT" ]; then
    echo "Database $DB_NAME does not exist. Creating now..."
    mysql -uroot -p"$MYSQL_ROOT_PASSWORD" -e "CREATE DATABASE $DB_NAME;"
    echo "Initializing database schema..."
    mysql -uroot -p"$MYSQL_ROOT_PASSWORD" $DB_NAME < /docker-entrypoint-initdb.d/schema.sql
    echo "Database $DB_NAME created and initialized."
else
    echo "Database $DB_NAME already exists. Skipping creation."
fi
