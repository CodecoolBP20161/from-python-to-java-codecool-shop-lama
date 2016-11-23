DROP TABLE IF EXISTS products, product_categories, suppliers;

CREATE TABLE suppliers
(
  id SERIAL PRIMARY KEY,
  name VARCHAR(100),
  description VARCHAR(255)
);

CREATE TABLE product_categories
(
  id SERIAL PRIMARY KEY,
  name VARCHAR(100),
  description VARCHAR(255),
  department VARCHAR(100)
);

CREATE TABLE products
(
  id SERIAL PRIMARY KEY,
  name VARCHAR(100),
  description VARCHAR(255),
  default_price FLOAT,
  default_currency VARCHAR(10),
  product_category INTEGER REFERENCES product_categories(id),
  supplier INTEGER REFERENCES suppliers(id),
  imageSource VARCHAR(250)
);