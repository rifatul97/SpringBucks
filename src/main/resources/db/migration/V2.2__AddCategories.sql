INSERT INTO parent_category (name)
VALUES ('Drinks'), ('Food'), ('Merchandise'), ('Gift Cards');

INSERT INTO sub_category (name)
VALUES ('Hot Coffees'), ('Hot Teas'), ('Hot Drinks'), ('Cold Coffees'), ('Iced Teas'), ('Cold Drinks'),
('Hot Breakfast'), ('Bakery'), ('Lunch'), ('Snacks & Sweets'),
('Cold Cups'), ('Tumblers'), ('Others'),
('Traditional'), ('Thank You');

INSERT INTO category_links (parent_id, sub_id) VALUES
(1,1), (1,2), (1,3), (1,4), (1,5), (1,6),
(2,7), (2,8), (2,9), (2,10),
(3,11), (3,12), (3,13), (4,14), (4,15);
