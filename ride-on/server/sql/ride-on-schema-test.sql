drop database if exists ride_on_test;

create database ride_on_test;

use ride_on_test;


create table `user` (
	user_id int primary key auto_increment,
    username varchar(50) not null unique,
    password_hash varchar(2048) not null,
    enabled bit not null default(1),
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

create table app_role (
    app_role_id int primary key auto_increment,
    `name` varchar(50) not null unique
);

create table user_role (
    user_id int not null,
    app_role_id int not null,
    constraint pk_user_role
        primary key (user_id, app_role_id),
    constraint fk_user_role_user_id
        foreign key (user_id)
        references `user`(user_id),
	constraint fk_user_role_role_id
        foreign key (app_role_id)
        references app_role(app_role_id)
);

insert into app_role (`name`) values
    ('USER'),
    ('ADMIN');

delimiter //
create procedure set_known_good_state()
begin

	delete from user_role;
    
	delete from rider;
	alter table rider auto_increment = 1;
    
    delete from trip;
    alter table trip auto_increment = 1;
    
    delete from car;
	alter table car auto_increment = 1;
    
    delete from `user`;
	alter table `user` auto_increment = 1;

    -- passwords are set to "P@ssw0rd!"
    insert into `user`
		(username, password_hash, enabled, first_name, last_name, banking_account, identification, preferences)
	values 
		('lkim@dev-10.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1, 'Liam', 'Kim', '01234abcde', '56789fghij', 'drive is all about talking and listening'),
        ('Mheine@dev-10.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1, 'Matthew', 'Heine', '1700144abcd', 'mt1039876', 'silence is life'),
        ('To Be Deleted', 'To Be Deleted', 1, 'To Be Deleted', 'To Be Deleted', 'To Be Deleted', 'To Be Deleted', 'To Be Deleted'); -- use user_id 3 to be deleted
	
    insert into car
		(insurance, registration, make, model, `year`, color, license_plate, user_id)
    values
		(true, true, 'Lamborghini', 'Aventador', '2022', 'orange', 'LAMBO123', 1),
        (false, false, 'Mercedes Benz', 'G-Wagon', '2023', 'brushed-silver', 'GWAG123', 2),
        (true, false, 'Honda', 'Accord', '1997', 'white', 'HON123', 1),
        (false, true, 'Ford', 'F150', '2005', 'black', 'FORD123', 2);

	insert into trip
		(departure, arrival, seats, price_per_seat, `date`, car_id) 
    values
		('Missoula, MT', 'Bozeman, MT', 4, 15.00, '2023-01-16', 2),
        ('Alpharetta, GA', 'Atlanta, GA', 1, 5.00, '2023-02-23', 1); 
        
	insert into rider
		(total_cost, payment_confirmation, user_id, trip_id)
    values
		(5.00, false, 1, 2),
		(15.00, true, 2, 1),
		(5.00, false, 3, 2);
        
	insert into user_role
	values
		(1, 2),
		(2, 1);

end //

delimiter ;


-- describe rider;

-- total_cost, payment_confirmation, user_id, trip_id,