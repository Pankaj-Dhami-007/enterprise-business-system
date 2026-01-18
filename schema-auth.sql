CREATE DATABASE IF NOT EXISTS bms_auth_db;
USE bms_auth_db;

CREATE TABLE clients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    client_name VARCHAR(150) NOT NULL,
    client_secret VARCHAR(50) NOT NULL UNIQUE,
    client_desc VARCHAR(250),

    is_active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE users_auth (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,

    client_id BIGINT NOT NULL,

    is_active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,

    CONSTRAINT fk_users_client
        FOREIGN KEY (client_id)
        REFERENCES clients(id)
        ON DELETE RESTRICT
);

CREATE TABLE refresh_tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    user_id BIGINT NOT NULL,
    token VARCHAR(255) NOT NULL UNIQUE,

    expiry_at DATETIME NOT NULL,

    is_revoked BOOLEAN NOT NULL DEFAULT FALSE,
    revoked_at DATETIME,

    created_at DATETIME NOT NULL,

    CONSTRAINT fk_refresh_user
        FOREIGN KEY (user_id)
        REFERENCES users_auth(id)
        ON DELETE CASCADE
);


CREATE INDEX idx_users_username ON users_auth(username);
CREATE INDEX idx_users_email ON users_auth(email);
CREATE INDEX idx_users_client ON users_auth(client_id);
CREATE INDEX idx_refresh_token ON refresh_tokens(token);
CREATE INDEX idx_refresh_token_valid ON refresh_tokens(token, is_revoked);


