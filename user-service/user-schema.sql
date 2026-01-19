CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    client_id BIGINT NOT NULL,
    role_name VARCHAR(50) NOT NULL,
    description VARCHAR(200),

    created_at DATETIME NOT NULL,
    updated_at DATETIME,

    CONSTRAINT uq_client_role UNIQUE (client_id, role_name)
);

CREATE TABLE permissions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    client_id BIGINT NOT NULL,
    permission_name VARCHAR(100) NOT NULL,
    description VARCHAR(200),

    created_at DATETIME NOT NULL,
    updated_at DATETIME,

    CONSTRAINT uq_client_permission UNIQUE (client_id, permission_name)
);

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    client_id BIGINT NOT NULL,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    full_name VARCHAR(150),
    mobile VARCHAR(15),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,

    role_id BIGINT NOT NULL,

    created_at DATETIME NOT NULL,
    updated_at DATETIME,

    CONSTRAINT uq_client_username UNIQUE (client_id, username),
    CONSTRAINT uq_client_email UNIQUE (client_id, email),

    CONSTRAINT fk_user_role
        FOREIGN KEY (role_id)
        REFERENCES roles(id)
);

CREATE TABLE role_permissions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,

    created_at DATETIME NOT NULL,
    updated_at DATETIME,

    CONSTRAINT uq_role_permission UNIQUE (role_id, permission_id),

    CONSTRAINT fk_rp_role
        FOREIGN KEY (role_id)
        REFERENCES roles(id),

    CONSTRAINT fk_rp_permission
        FOREIGN KEY (permission_id)
        REFERENCES permissions(id)
);
