drop table if exists MybatisBean cascade;
create table MybatisBean(
	id varchar(20) ,
	name varchar(20) ,
	age integer ,
	sex boolean ,
	primary key (id)
);


drop table if exists JdbcBean cascade;
create table JdbcBean(
	id varchar(20) ,
	name varchar(20) ,
	age integer ,
	sex boolean ,
	primary key (id)
);
