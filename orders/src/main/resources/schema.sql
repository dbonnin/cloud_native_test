CREATE TABLE Orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created TIMESTAMP,
    total DECIMAL(10, 2),
    creditcard VARCHAR(255),
    product_id BIGINT,
    quantity INT,
    order_status VARCHAR(255)
);