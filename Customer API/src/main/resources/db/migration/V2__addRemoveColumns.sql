alter table customer 
	drop foreign key customer_ibfk_1,
	drop foreign key customer_ibfk_2;
	
alter table customer 
	drop column mainAddress,
	drop column adresses;

alter table address
	add column costumer_id int,
    add foreign key fk_customer_id(costumer_id) references customer(id);