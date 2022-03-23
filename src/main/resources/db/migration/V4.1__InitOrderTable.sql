CREATE TYPE order_status AS ENUM('NEW', 'ONQUEUE', 'FULFILLING', 'CANCELLED', 'FULFILLED');

CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    date_added DATE NOT NULL DEFAULT CURRENT_DATE,
    last_updated DATE NOT NULL DEFAULT CURRENT_DATE,
    status order_status NOT NULL
);

CREATE TABLE IF NOT EXISTS user_orders (
    user_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS cart_item (
    id SERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    last_updated DATE NOT NULL DEFAULT CURRENT_DATE,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);

