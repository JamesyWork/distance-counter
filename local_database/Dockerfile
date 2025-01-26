FROM mysql:8.0-debian

ENV MYSQL_ROOT_PASSWORD=password
ENV MYSQL_USER=sa
ENV MYSQL_PASSWORD=password

# Copy initialization files
COPY ukpostcodes_tablemysql.sql /docker-entrypoint-initdb.d/schema.sql
COPY init-db.sh /docker-entrypoint-initdb.d/init-db.sh

# Make the script executable
RUN chmod +x /docker-entrypoint-initdb.d/init-db.sh