CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name varchar(100) NOT NULL,
    last_name varchar(100) NOT NULL,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    date_created DATE NOT NULL DEFAULT CURRENT_DATE
);--ENGINE=INNODB;

CREATE TABLE role (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(100) DEFAULT NULL
);--ENGINE=INNODB;

CREATE TABLE users_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT DEFAULT 1,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
);--ENGINE=INNODB;