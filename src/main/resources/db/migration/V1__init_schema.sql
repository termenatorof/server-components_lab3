CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL
);

CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            user_id BIGINT NULL,
                            CONSTRAINT fk_categories_user
                                FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE records (
                         id BIGSERIAL PRIMARY KEY,
                         user_id BIGINT NOT NULL,
                         category_id BIGINT NOT NULL,
                         created_at VARCHAR(64) NOT NULL,
                         amount NUMERIC(19, 2) NOT NULL,
                         CONSTRAINT fk_records_user
                             FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                         CONSTRAINT fk_records_category
                             FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);