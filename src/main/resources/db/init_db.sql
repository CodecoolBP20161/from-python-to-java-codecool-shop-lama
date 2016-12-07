DROP TABLE IF EXISTS products, product_categories, customer, suppliers, order_product_connection, orders, address;

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

CREATE TABLE address
(
  id SERIAL PRIMARY KEY,
  address_uuid VARCHAR(255),
  country VARCHAR(100),
  city VARCHAR(100),
  zip_code VARCHAR(20),
  address VARCHAR(255)
);

CREATE TABLE customer
(
  id SERIAL PRIMARY KEY,
  customer__uuid VARCHAR(255),
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  email VARCHAR(255),
  phone_number VARCHAR(255),
  billing_address INTEGER REFERENCES address(id),
  shipping_address INTEGER REFERENCES address(id)
);

CREATE TABLE orders
(
  id SERIAL PRIMARY KEY,
  order_uuid VARCHAR(255),
  timestamp TIMESTAMP,
  status VARCHAR(20),
  billing_address INTEGER REFERENCES address(id),
  shipping_address INTEGER REFERENCES address(id),
  customer INTEGER REFERENCES customer(id)
);

CREATE TABLE order_product_connection
(
  id SERIAL PRIMARY KEY,
  order_id INTEGER REFERENCES orders(id),
  product_id INTEGER REFERENCES products(id),
  quantity INTEGER
);