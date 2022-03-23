CREATE TABLE parent_category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE sub_category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE category_links (
    parent_id BIGINT NOT NULL,
    sub_id BIGINT NOT NULL,
    FOREIGN KEY (parent_id) REFERENCES parent_category (id),
    FOREIGN KEY (sub_id) REFERENCES sub_category (id)
);

