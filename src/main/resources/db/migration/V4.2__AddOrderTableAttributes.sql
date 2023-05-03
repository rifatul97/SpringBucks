CREATE TABLE IF NOT EXISTS order_location (
    id SERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    order_location VARCHAR(100) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id)
);

CREATE TABLE IF NOT EXISTS users_orders_archives (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    date_fulfilled DATE NOT NULL DEFAULT CURRENT_DATE,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);