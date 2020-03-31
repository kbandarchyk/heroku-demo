create table customer_stats (
  id bigserial not null,
  number_of_customers int not null,
  created_date Date not null,
  primary key (id)
);