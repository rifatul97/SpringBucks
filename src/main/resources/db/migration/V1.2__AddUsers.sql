INSERT INTO users(first_name, last_name, email, password)
VALUES ('rifat', 'karim', 'rifat97@gmail.com', 'pass'),
('john', 'carter', 'john1990@yahoo.com', 'pass'),
('honey', 'moon', 'honnymoon@amazon.com', 'pass'),
('chance', 'robert', 'croboertisamazing@gmail.com', 'pass');

INSERT INTO testUser (name, description) VALUES
('ROLE_CUSTOMER', 'Can track order and check their profile.'),
('ROLE_BARISTA', 'Has access to customers orders.'),
('ROLE_ADMIN', 'Has access to all.');

INSERT INTO users_roles (user_id)
VALUES (3), (4);

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 3), (2, 2);
