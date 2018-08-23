create database if not exists `bank_db`;

create table `bank_db`.`bank` (
	`id` int(4) not null primary key auto_increment,
  `bank` varchar(50) not null
);

create table `bank_db`.`user` (
	`id` int(4) not null primary key auto_increment,
  `firstName` varchar(50) not null,
  `lastName` varchar(50) not null,
  `middleName` varchar(50) not null,
  `login` varchar(50) not null,
  `password` varchar(50) not null,
  `email` varchar(50) not null
);

create table `bank_db`.`roles` (
	`id` int(4) not null primary key auto_increment,
  `user_id` int(4) not null,
  `bank_id` int(4) not null,
  `role` varchar(50) default 'ROLE_USER',
  foreign key (`bank_id`) references `bank_db`.`bank` (`id`),
  foreign key (`user_id`) references `bank_db`.`user` (`id`)
);

create table `bank_db`.`phones` (
	`id` int(4) not null primary key auto_increment,
  `user_id` int(4) not null,
  `phone` varchar(50) not null,
  foreign key (`user_id`) references `bank_db`.`phones`(`id`)
);

insert into `bank_db`.`user`(`firstName`,`lastName`,`middleName`,`login`,`password`,`email`) values
('Artem', 'Petrov',   'Oleksandrovich', 'login',  'password',  'vitaliy.abrams@gmail.com'),
('Jason', 'Statham',  'Artemovich',     'login1', 'password1', 'jason.statham@gmail.com'),
('Robert','Downey',   'Oleksiovich',    'login2', 'password2', 'robert.downey@gmail.com'),
('Chris', 'Hemsworth','Maksymovich',    'login3', 'password3', 'chris.hemsworth@gmail.com'),
('John',  'Carter',   'Ivanocich',      'login4', 'password4', 'john.carter@gmail.com');

insert into `bank_db`.`bank`(`bank`) values
('PRIVAT_BANK'), ('ALFA_BANK'), ('MEGA_BANK'), ('IDEA_BANK'), ('KREDO_BANK');

insert into `bank_db`.`roles`(`user_id`, `bank_id`, `role`) values
(1, 1, 'ROLE_USER'), (1, 1, 'ROLE_ADMIN'), (2, 1, 'ROLE_EMPLOYEE'), (2, 3, 'ROLE_ADMINISTRATOR'), (3, 1, 'ROLE_DIRECTOR');

insert into `bank_db`.`phones`(`user_id`, `phone`) values
(1, '+380663231502'), (1, '+380959058192'), (2, '+380998902355'),
(3, '+380663332255'), (4, '+380998881122'), (5, '+380990002455');
