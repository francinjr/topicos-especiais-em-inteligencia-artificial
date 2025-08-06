INSERT INTO tb_users (id, email, password, role, is_password_changed, deleted)
VALUES (9223372036854775807, 'admin@gmail.com',
        '$2a$12$y6Lbu0GRYEAaXqqUpt3JsuQ7h1066g0WjBkayzTmWU/po6NGUpY26', 'ADMIN', TRUE, FALSE);


INSERT INTO tb_admins (id, user_id)
VALUES (9223372036854775807, 9223372036854775807);