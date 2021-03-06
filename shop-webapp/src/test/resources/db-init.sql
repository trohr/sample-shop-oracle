
drop table product_definition if exists

create table product_definition (
	id bigint generated by default as identity,
	caption varchar(256),
	image_url varchar(256),
	name varchar(128) not null,
	price decimal(12,2) not null,
	specification varchar(2048),
	tags varchar(256),
	version integer,
	primary key (id)
)