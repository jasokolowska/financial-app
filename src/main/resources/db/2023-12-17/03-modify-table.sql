--liquibase formatted sql
--changeset jasokolowska:11
ALTER TABLE expenses ALTER COLUMN budget_id DROP NOT NULL;
ALTER TABLE expenses ALTER COLUMN category_id DROP NOT NULL;
