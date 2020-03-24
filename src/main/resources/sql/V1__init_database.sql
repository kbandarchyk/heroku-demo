create table customer (
  id bigserial not null,
  first_name varchar(256) not null,
  last_name varchar(256) not null,
  phone_number varchar(20),
  created_date timestamp not null,
  is_deleted boolean not null,
  primary key (id) );