DROP SCHEMA IF EXISTS news;

CREATE SCHEMA news;

USE news;

CREATE TABLE publishers (
	source_name varchar(128) not null,
	api_id varchar(128), -- identifier used by newsAPI, nullable on their end
	description text,
	url varchar(256),
	category varchar(128),
	language varchar(64),
	country varchar(64),
	
	PRIMARY KEY(source_name)
);


CREATE TABLE articles (
  article_id int not null auto_increment,
  author varchar(128),
  title text not null,
  description text not null,
  url varchar(256) not null,
  urlImage varchar(256) not null,
  published_at timestamp not null,
  
  -- foreign key
  source_name varchar(128) not null,

  PRIMARY KEY(article_id),
  CONSTRAINT fk_source_name
  	FOREIGN KEY(source_name)
  	REFERENCES publishers(source_name)
);



