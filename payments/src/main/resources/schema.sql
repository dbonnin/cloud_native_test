CREATE TABLE IF NOT EXISTS payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    received TIMESTAMP,
    total DECIMAL(10, 2),
    creditcard VARCHAR(255),
    order_id BIGINT,
    payment_status VARCHAR(255)
);