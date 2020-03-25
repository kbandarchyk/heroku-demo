create table users (
  id bigserial not null,
  first_name varchar(256) not null,
  last_name varchar(256) not null,
  login varchar(20) not null,
  password varchar(20) not null,
  is_deleted boolean not null,
  primary key (id) );

INSERT INTO users( first_name, last_name, login, password, id_deleted ) VALUES ( 'Иван', 'Иванов', 'admin', '$2y$12$z/tAJFs2P86OoQ5OpXJqG.OvjhwhMuyaK5HLZJkIaSZny8w18Jz16', false );
