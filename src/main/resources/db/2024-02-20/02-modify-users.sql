--liquibase formatted sql
--changeset jasokolowska:10
ALTER TABLE users
    ADD COLUMN enabled BOOLEAN;

--changeset jasokolowska:11
UPDATE users
SET enabled = true;

--changeset jasokolowska:12
ALTER TABLE users
    ALTER COLUMN enabled SET NOT NULL;