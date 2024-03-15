--liquibase formatted sql
--changeset jasokolowska:test_1
INSERT INTO users (user_id, username, password, first_name, last_name, enabled)
VALUES
    ((SELECT COALESCE(MAX(user_id), 0) + 1 FROM users), 'john_doe@gmail.com', '$2a$10$fxU4L6dP3uWWUpLbHYo.YOy.cH.7xVYdkJO2KIMrAVwSkgyrcA9YS', 'John', 'Doe', true),
    ((SELECT COALESCE(MAX(user_id), 0) + 2 FROM users), 'jane_smith@gmail.com', '$2a$10$fxU4L6dP3uWWUpLbHYo.YOy.cH.7xVYdkJO2KIMrAVwSkgyrcA9YS', 'Jane', 'Smith', true),
    ((SELECT COALESCE(MAX(user_id), 0) + 3 FROM users), 'bob_jones@gmail.com', '$2a$10$fxU4L6dP3uWWUpLbHYo.YOy.cH.7xVYdkJO2KIMrAVwSkgyrcA9YS', 'Bob', 'Jones', true);

--changeset jasokolowska:test_2
INSERT INTO authorities (username, authority) values ('john_doe@gmail.com','ROLE_USER'), ('jane_smith@gmail.com','ROLE_USER'), ('bob_jones@gmail.com','ROLE_USER');

--changeset jasokolowska:test_3
INSERT INTO expense_categories (category_name)
VALUES
    ('Groceries'),
    ('Electricity'),
    ('Transport');

--changeset jasokolowska:test_4
INSERT INTO budgets (user_id, budget_name, start_date, end_date, total_amount, description)
VALUES
    (1, 'Monthly Budget', '2023-01-01', '2023-01-31', 2000.00, 'Monthly expenses'),
    (2, 'Personal Budget', '2023-01-01', '2023-02-28', 1500.00, 'Personal expenses');

--changeset jasokolowska:test_5
INSERT INTO expenses (user_id, budget_id, category_id, amount, expense_date, description)
VALUES
    (1, 1, 1, 150.00, '2023-01-05T13:01:58.618Z', 'Groceries for the week'),
    (2, 2, 2, 50.00, '2023-01-10T13:01:58.618Z', 'Electricity bill'),
    (1, 1, 1, 150.00, '2023-01-05T13:01:58.618Z', 'Groceries for the week'),
    (2, 2, 2, 50.00, '2023-07-17T13:01:58.618Z', 'Electricity bill'),
    (2, 2, 2, 123.00, '2023-12-10T13:01:58.618Z', 'Toys'),
    (2, 2, 2, 14.99, '2023-08-10T13:01:58.618Z', 'Cleaning'),
    (2, 2, 2, 200.23, '2023-01-10T13:01:58.618Z', 'Hairdresser');

--changeset jasokolowska:test_6
INSERT INTO budget_category_allocation ( budget_id, category_id, allocated_amount)
VALUES
    (1, 1, 1000.00),
    (1, 2, 500.00),
    (2, 3, 700.00);

--changeset jasokolowska:test_7
INSERT INTO incomes (user_id, amount, income_date, description)
VALUES
    (1, 2000.00, '2023-01-15', 'Monthly salary'),
    (1, 1500.00, '2023-01-20', 'Freelance income');
