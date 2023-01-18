drop database if exists ride_on;

create database ride_on;

use ride_on;

create table `user` (
	user_id int primary key auto_increment,
	first_name varchar(255) not null,
    last_name varchar(255) not null,
    banking_account varchar(255) not null,
    identification varchar(255) not null,
    preferences varchar(255) default null
);

create table car (
	car_id int primary key auto_increment,
    insurance boolean not null,
    registration boolean not null,
    make varchar(50) not null,
    model varchar(50) not null,
    `year` varchar(20) default null,
    color varchar(20) not null,
    license_plate varchar(20),
    user_id int not null,
    constraint fk_car_user
		foreign key (user_id)
        references `user`(user_id)
);

create table trip (
	trip_id int primary key auto_increment,
    departure varchar(255) not null,
    arrival varchar(255) not null,
    seats int default 0,
	price_per_seat decimal (10, 2) not null,
    `date` date not null,
    car_id int not null,
    constraint fk_trip_car
		foreign key (car_id)
        references car(car_id)
);

create table rider (
	rider_id int primary key auto_increment,
    total_cost decimal (10, 2) not null,
    payment_confirmation boolean default true,
    user_id int not null,
    trip_id int not null,
	constraint fk_rider_user
		foreign key (user_id)
		references `user`(user_id),
	constraint fk_rider_trip
		foreign key (trip_id)
        references trip(trip_id)
);