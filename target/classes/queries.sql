create database if not exists supermarket;
use supermarket;

create table if not exists user(
user_id int auto_increment not null,
username varchar(50) not null,
password varchar(50) not null,
name varchar(50) not null,
email varchar(50) not null,
budget decimal(20,2) not null,
user_type varchar(50) not null,
primary key(user_id)
);

create table if not exists product(
product_id int auto_increment not null,
product_name varchar(50) not null,
quantity int not null,
price decimal(20,2) not null,
unit varchar(50) not null,
category decimal(20,2) not null,
primary key(product_id)
);

create table if not exists sales(
sales_id int auto_increment not null,
order_number int not null,
product int not null,
quantity int not null,
user int not null,
purchase_date date not null,
primary key(sales_id),
foreign key(product) references product(product_id),
foreign key(user) references user(user_id)
);

create table if not exists balance(
balance_id int auto_increment not null,
user int not null,
bank_account varchar(50) not null,
amount decimal(20,2) not null,
operation varchar(50) not null,
operation_date date not null,
primary key(balance_id),
foreign key(user) references user(user_id)
);