#!/bin/bash
set -e

POSTGRES="psql --username ${POSTGRES_USER}"

$POSTGRES <<-EOSQL

CREATE USER heroku_demo_admin WITH PASSWORD '123a456B';

CREATE DATABASE heroku_demo_db;

GRANT ALL PRIVILEGES ON DATABASE heroku_demo_db TO heroku_demo_admin;

EOSQL

POSTGRES="psql --username heroku_demo_admin heroku_demo_db"

$POSTGRES <<-EOSQL

CREATE TABLE customer (
  id bigserial not null,
  first_name varchar(256) not null,
  last_name varchar(256) not null,
  phone_number varchar(20),
  created_date timestamp not null,
  is_deleted boolean not null,
  primary key (id) );

CREATE TABLE users (
  id bigserial not null,
  first_name varchar(256) not null,
  last_name varchar(256) not null,
  login varchar(256) not null,
  password varchar(256) not null,
  is_deleted boolean not null,
  primary key (id) );

INSERT INTO users( first_name, last_name, login, password, is_deleted ) VALUES ( 'Иван', 'Иванов', 'admin', '123a456B', false );

CREATE TABLE customer_stats (
  id bigserial not null,
  number_of_customers int not null,
  created_date Date not null,
  primary key (id)
);

EOSQL
