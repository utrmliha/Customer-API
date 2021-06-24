alter table customer 
	drop column createdAt,
	drop column updateAt;

alter table customer
	add column createdAt varchar(19),
	add column updateAt varchar(19);