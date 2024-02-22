--liquibase formatted sql
--changeset jasokolowska:13
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--changeset jasokolowska:14
ALTER TABLE users
    ADD COLUMN uuid UUID DEFAULT uuid_generate_v4();

--changeset jasokolowska:15
UPDATE users
SET uuid = uuid_generate_v4();
