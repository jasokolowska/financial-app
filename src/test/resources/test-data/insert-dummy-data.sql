INSERT INTO users (username, password, first_name, last_name)
VALUES
    ('john_doe', 'password123', 'John', 'Doe'),
    ('jane_smith', 'pass456', 'Jane', 'Smith'),
    ('bob_jones', 'secret789', 'Bob', 'Jones');

INSERT INTO expense_categories (category_name)
VALUES
    ('Groceries'),
    ('Electricity'),
    ('Transport');

INSERT INTO budgets (user_id, budget_name, start_date, end_date, total_amount, description)
VALUES
    (1, 'Monthly Budget', '2023-01-01', '2023-01-31', 2000.00, 'Monthly expenses'),
    (2, 'Personal Budget', '2023-01-01', '2023-02-28', 1500.00, 'Personal expenses');

INSERT INTO expenses (user_id, budget_id, category_id, amount, expense_date, description)
VALUES
    (1, 1, 1, 150.00, '2023-01-05', 'Groceries for the week'),
    (2, 2, 2, 50.00, '2023-01-10', 'Electricity bill'),
    (1, 1, 1, 150.00, '2023-01-05', 'Groceries for the week'),
    (2, 2, 2, 50.00, '2023-07-17', 'Electricity bill'),
    (2, 2, 2, 123.00, '2023-12-10', 'Toys'),
    (2, 2, 2, 14.99, '2023-08-10', 'Cleaning'),
    (2, 2, 2, 200.23, '2023-01-10', 'Hairdresser');

INSERT INTO budget_category_allocation ( budget_id, category_id, allocated_amount)
VALUES
    (1, 1, 1000.00),
    (1, 2, 500.00),
    (2, 3, 700.00);

INSERT INTO incomes (user_id, amount, income_date, description)
VALUES
    (1, 2000.00, '2023-01-15', 'Monthly salary'),
    (1, 1500.00, '2023-01-20', 'Freelance income');
