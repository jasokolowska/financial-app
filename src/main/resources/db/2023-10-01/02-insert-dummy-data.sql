--liquibase formatted sql
--changeset jasokolowska:7
INSERT INTO users (user_id, username, password, first_name, last_name)
VALUES
    (1, 'john_doe', 'password123', 'John', 'Doe'),
    (2, 'jane_smith', 'pass456', 'Jane', 'Smith'),
    (3, 'bob_jones', 'secret789', 'Bob', 'Jones');

--changeset jasokolowska:8
INSERT INTO expense_categories (category_id, category_name)
VALUES
    (1, 'Groceries'),
    (2, 'Utilities'),
    (3, 'Entertainment');

--changeset jasokolowska:9
INSERT INTO budgets (budget_id, user_id, budget_name, start_date, end_date, total_amount, description)
VALUES
    (1, 1, 'Monthly Budget', '2023-01-01', '2023-01-31', 2000.00, 'Monthly expenses'),
    (2, 2, 'Personal Budget', '2023-01-01', '2023-02-28', 1500.00, 'Personal expenses');

--changeset jasokolowska:10
INSERT INTO expenses (expense_id, user_id, budget_id, category_id, amount, expense_date, description)
VALUES
    (1, 1, 1, 1, 150.00, '2023-01-05', 'Groceries for the week'),
    (2, 2, 2, 2, 50.00, '2023-01-10', 'Electricity bill');

--changeset jasokolowska:11
INSERT INTO budget_category_allocation (allocation_id, budget_id, category_id, allocated_amount)
VALUES
    (1, 1, 1, 1000.00),
    (2, 1, 2, 500.00),
    (3, 2, 3, 700.00);

--changeset jasokolowska:12
INSERT INTO incomes (income_id, user_id, amount, income_date, description)
VALUES
    (1, 1, 2000.00, '2023-01-15', 'Monthly salary'),
    (2, 2, 1500.00, '2023-01-20', 'Freelance income');
