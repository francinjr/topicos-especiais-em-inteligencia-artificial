CREATE TABLE tb_users
(
    id                  BIGSERIAL PRIMARY KEY,
    email               VARCHAR(255),
    password            VARCHAR(255),
    role                VARCHAR(50),
    is_password_changed BOOLEAN,
    deleted 			BOOLEAN
);

CREATE TABLE tb_admins
(
    id        BIGSERIAL PRIMARY KEY,
    user_id   BIGINT,
    FOREIGN KEY (user_id) REFERENCES tb_users (id) ON DELETE CASCADE
);