--liquibase formatted sql
--changeset jasokolowska:1
CREATE TABLE IF NOT EXISTS users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255)
);

--changeset jasokolowska:2
CREATE TABLE IF NOT EXISTS expense_categories (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL
);

--changeset jasokolowska:4
CREATE TABLE IF NOT EXISTS budgets (
    budget_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    budget_name VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    description TEXT,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);


--changeset jasokolowska:3
CREATE TABLE IF NOT EXISTS expenses (
    expense_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    budget_id INT NOT NULL,
    category_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    expense_date DATE NOT NULL,
    description TEXT,
    CONSTRAINT fk_user_expense FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_budget_expense FOREIGN KEY (budget_id) REFERENCES budgets (budget_id),
    CONSTRAINT fk_category_expense FOREIGN KEY (category_id) REFERENCES expense_categories (category_id)
);

--changeset jasokolowska:5
CREATE TABLE IF NOT EXISTS budget_category_allocation (
    allocation_id SERIAL PRIMARY KEY,
    budget_id INT NOT NULL,
    category_id INT NOT NULL,
    allocated_amount DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_budget FOREIGN KEY (budget_id) REFERENCES budgets (budget_id),
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES expense_categories (category_id)
);

--changeset jasokolowska:6
CREATE TABLE IF NOT EXISTS incomes (
    income_id INT  PRIMARY KEY,
    user_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    income_date DATE NOT NULL,
    description TEXT,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);
