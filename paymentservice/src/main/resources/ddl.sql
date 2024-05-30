CREATE TABLE payment_events(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    buyer_id BIGINT NOT NULL,
    is_payment_done BOOLEAN NOT NULL DEFAULT FALSE,
    payment_key VARCHAR(255) UNIQUE ,
    order_id VARBINARY(255) UNIQUE ,
    type ENUM('NORMAL') NOT NULL,
    order_name VARCHAR(255) NOT NULL,
    method ENUM('EASY_PAY'),
    psp_raw_data JSON,
    approved_at DATETIME,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payment_orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    payment_event_id BIGINT NOT NULL,
    seller_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    order_id VARBINARY(255) UNIQUE,
    order_name VARCHAR(255) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    currency ENUM('USD') NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
)