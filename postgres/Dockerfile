FROM postgres:9.4

LABEL maintaner = "konstantin.bondarchuk@vrpconsulting.com"

ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD postgres
ENV POSTGRES_DB postgres

ADD init_admin_db.sh              /docker-entrypoint-initdb.d/

EXPOSE 5432
