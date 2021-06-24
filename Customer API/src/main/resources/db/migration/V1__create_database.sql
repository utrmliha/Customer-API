create table address(
id int not null auto_increment,
state enum('AC','AL','AP','AM','BA','CE','DF','ES','GO','MA','MT','MS','MG','PA','PB','PR','PE','PI','RJ','RN','RS','RO','RR','SC','SP','SE','TO'),
city varchar(29),
neighborhood varchar(30),
zipCode varchar(9),
street varchar(35),
`number` varchar(7),
additionalInformation varchar(30),
main boolean,
primary key (id)
)default charset = utf8;

create table customer(
id int not null auto_increment,
uuid char(36) null,
`name` varchar(100),
birthDate varchar(10),
cpf varchar(14) unique,
gender enum('MASCULINO','FEMININO'),
mainAddress int,
adresses int,
createdAt datetime,
updateAt datetime,
primary key (id),
foreign key (mainAddress) references address(id),
foreign key (adresses) references address(id)
)default charset = utf8;

DELIMITER ;;
CREATE TRIGGER before_insert_customer
BEFORE INSERT ON customer
FOR EACH ROW
BEGIN
  IF new.uuid IS NULL THEN
    SET new.uuid = uuid();
  END IF;
END
;;