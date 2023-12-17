--liquibase formatted sql
--changeset jasokolowska:10
INSERT INTO expenses (expense_id, user_id, budget_id, category_id, amount, expense_date, description)
VALUES
    (3, 1, 1, 1, 150.00, '2023-01-05', 'Groceries for the week'),
    (4, 2, 2, 2, 50.00, '2023-07-17', 'Electricity bill'),
    (5, 2, 2, 2, 123.00, '2023-12-10', 'Toys'),
    (6, 2, 2, 2, 14.99, '2023-08-10', 'Cleaning'),
    (7, 2, 2, 2, 200.23, '2023-01-10', 'Hairdresser');

