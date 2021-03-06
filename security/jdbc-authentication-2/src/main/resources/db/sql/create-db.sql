CREATE  TABLE users (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(245) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username));
  
CREATE TABLE user_roles (
  user_role_id int(11) NOT NULL,
  username varchar(45) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));

CREATE  TABLE customer (
  id int(11) NOT NULL ,
  name VARCHAR(245) NOT NULL ,
  country VARCHAR(245) NOT NULL ,
  PRIMARY KEY (id)
);