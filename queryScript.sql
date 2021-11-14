use coffeeshop;

-- Human
create table account(
code varchar(20) not null primary key,
user varchar (50) not null,
password varchar(100) not null default "48690",
status int not null default 1,
url varchar(150) not null default "src\\images\\default.jpg"
);

create table amount_each_part(
id int not null auto_increment primary key,
partCode ENUM('BARTENDER', 'SECURITY', 'COOKER', 'SERVANT', 'MANAGER', 'HOUSEKEEPER', 'CASHIER') not null ,
amount int not null default 0
);

create table basic_salary(
code varchar(20) not null primary key,
basicSalary double not null 
);

create table bonus(
code varchar(20) not null primary key,
bonus double not null
);

create table dayoff(
code varchar(20) not null primary key,
haftPermiss int not null default 0,
haftNonPermiss int not null default 0,
allPermiss int not null default 0,
allNonPermiss int not null default 0
);

create table overtime(
code varchar(20) not null primary key,
overtime int not null default 0
);

create table part(
code varchar(20) not null primary key,
part enum('BARTENDER', 'SECURITY', 'COOKER', 'SERVANT', 'MANAGER', 'HOUSEKEEPER', 'CASHIER') not null
);

create table rate_profit(
code varchar(20) not null primary key,
rateProfit double not null default 0
);

create table sex(
code varchar(20) not null primary key,
sex enum("MALE", "FEMALE", "OTHER")
);

create table shift(
code varchar(20) not null primary key,
shift enum("DAY", "NIGHT", "ALL", "")
);

CREATE TABLE human (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
code VARCHAR(20) NOT NULL,
name VARCHAR(100) NOT NULL,
yearOfBirth DATE NOT NULL,
age INT NOT NULL,
address VARCHAR(100) NOT NULL,
idCard VARCHAR(20) NOT NULL,
startDate DATE NOT NULL,
phoneNumber VARCHAR(12) NOT NULL,

CONSTRAINT fk_human_account FOREIGN KEY(code) REFERENCES account(code), 
CONSTRAINT fk_human_basic_salary FOREIGN KEY(code) REFERENCES basic_salary(code),
CONSTRAINT fk_human_bonus FOREIGN KEY(code) REFERENCES bonus(code), 
CONSTRAINT fk_human_dayoff FOREIGN KEY(code) REFERENCES dayoff(code), 
CONSTRAINT fk_human_overtime FOREIGN KEY(code) REFERENCES overtime(code), 
CONSTRAINT fk_human_part FOREIGN KEY(code) REFERENCES part(code), 
CONSTRAINT fk_human_rate_profit FOREIGN KEY(code) REFERENCES rate_profit(code), 
CONSTRAINT fk_human_sex FOREIGN KEY(code) REFERENCES sex(code), 
CONSTRAINT fk_human_shift FOREIGN KEY(code) REFERENCES shift(code)
);

insert into account(code, user, status)
values ("MANAGER00001", "MANAGER00001", 0); 
insert into amount_each_part(partCode, amount)
values('BARTENDER',0),('SECURITY',0),('COOKER',0),('SERVANT',0),('MANAGER',1),('HOUSEKEEPER',0), ('CASHIER',0);
insert into basic_salary(code, basicSalary)
values ("MANAGER00001", 15000000);
insert into bonus(code, bonus)
values("MANAGER00001", 700000);
insert into dayoff(code) values("MANAGER00001");
insert into overtime(code, overtime) values ("MANAGER00001", 30);
insert into part(code, part) values("MANAGER00001", "MANAGER");
insert into rate_profit(code, rateProfit) values ("MANAGER00001", 1.2);
insert into sex(code, sex) values ("MANAGER00001", "MALE");
insert into shift (code, shift) values ("MANAGER00001", "");
insert into human (code, name, yearOfBirth, age, address, idCard, startDate, phoneNumber) values
("MANAGER00001", "Nguyễn Trung Kiên", "2001-02-15", 19, "Lái Thiêu, Thuận An, Bình Dương", "001201018887", "2019-05-24", "0982482975");


-- product
create table cost_product(
code varchar(30) not null primary key,
saleCost double not null,
purchaseCost double not null
);

create table sale_time(
code varchar(30) not null primary key,
morning tinyint not null default 0,
noon tinyint not null default 0,
afternoon tinyint not null default 0,
night tinyint not null default 0,
late tinyint not null default 0
);

create table info_expir_manuf(
code varchar(30) not null primary key,
expiration date not null,
manufacture date not null
);

create table amount_each_product(
code varchar(30) not null primary key,
amountProduct int not null default 0
);

create table kind_product(
code varchar(30) not null primary key,
kindProduct varchar(50) not null
);

create table amount_each_kind_product(
amountKindFood int not null default 0,
amountKindDrink int not null default 0
);

create table product(
id int not null auto_increment primary key,
code varchar(30) not null,
name varchar(30) not null,

constraint fk_product_cost_product
foreign key(code)
references cost_product(code),

constraint fk_product_sale_time
foreign key(code)
references sale_time(code),

constraint fk_product_info_expir_manuf
foreign key(code)
references info_expir_manuf(code),

constraint fk_product_amount_each_product
foreign key(code)
references amount_each_product(code),

constraint fk_product_kind_product
foreign key(code)
references kind_product(code)

);

insert into amount_each_kind_product(amountKindFood, amountKindDrink)
values (0,0);

-- infrastructure
create table booked_table(
id int not null auto_increment primary key,
codeTable varchar(30) not null,
codeHuman varchar(20) not null 
);

create table amount_table(
amount int not null default 0
);

create table info_table(
id int not null auto_increment primary key,
code varchar(30) not null,
name varchar(100) not null default 0,
capacity int not null default 2,
status boolean not null default false,
material varchar(50) not null,
purchaseDate date not null
);

insert into amount_table(amount)
values(0);

-- revenue
create table revenue(
codeTable varchar(30),
infoDate date not null,
infoTime varchar(8) not null,
amountProfit double not null
);