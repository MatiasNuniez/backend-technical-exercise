
-- BASE DE DATOS: db_clients  (ms-clients -> puerto 8080)

\connect db_clients;

CREATE TABLE IF NOT EXISTS clients (
    client_id    BIGSERIAL    PRIMARY KEY,
    name         VARCHAR(255),
    gender       VARCHAR(50),
    age          INTEGER,
    identification VARCHAR(255) UNIQUE NOT NULL,
    address      VARCHAR(255),
    phone        VARCHAR(50),
    password     VARCHAR(255),
    state        BOOLEAN
);

INSERT INTO clients (name, gender, age, identification, address, phone, password, state)
VALUES
    ('Jose Lema',    'M', 30, '1234567890', 'Otavalo sn y principal', '098254785', 'abc123', TRUE),
    ('Marianela Montalvo', 'F', 25, '0987654321', 'Amazonas y  NNUU',       '097548965', 'abc123', TRUE),
    ('Juan Osorio',  'M', 20, '1122334455', '13 junio y Equinoccial',  '098874587', 'abc123', TRUE);



-- BASE DE DATOS: db_accounts  (ms-accounts -> puerto 8081)

\connect db_accounts;

CREATE TABLE IF NOT EXISTS accounts (
    account_id      BIGSERIAL       PRIMARY KEY,
    account_number  VARCHAR(255)    UNIQUE NOT NULL,
    account_type    VARCHAR(50),
    initial_balance NUMERIC(19, 2),
    current_balance NUMERIC(19, 2),
    state           BOOLEAN,
    client_id       BIGINT
);

CREATE TABLE IF NOT EXISTS movements (
    movement_id     BIGSERIAL       PRIMARY KEY,
    date            TIMESTAMP,
    movement_type   VARCHAR(50),
    value           NUMERIC(19, 2),
    balance         NUMERIC(19, 2),
    account_id      BIGINT NOT NULL,
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);

INSERT INTO accounts (account_number, account_type, initial_balance, current_balance, state, client_id)
VALUES
    ('478758',  'Ahorro',   2000.00, 2000.00, TRUE,  1),
    ('225487',  'Corriente', 100.00,  100.00, TRUE,  2),
    ('495878',  'Ahorro',      0.00,    0.00, TRUE,  3),
    ('496825',  'Ahorro',  540.00,  540.00,  TRUE,  2),
    ('585545',  'Corriente', 1000.00, 1000.00, TRUE, 1);

INSERT INTO movements (date, movement_type, value, balance, account_id)
VALUES
    ('2024-01-05 00:00:00', 'Retiro',    -575.00,  1425.00, 1),
    ('2024-01-10 00:00:00', 'Deposito',   600.00,   700.00, 2),
    ('2024-02-13 00:00:00', 'Deposito',     0.00,     0.00, 3),
    ('2024-02-08 00:00:00', 'Retiro',    -540.00,     0.00, 4),
    ('2024-01-30 00:00:00', 'Deposito',   150.00,  1150.00, 5);
