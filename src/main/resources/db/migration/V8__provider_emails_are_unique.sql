ALTER TABLE skip_diving.provider
    DROP COLUMN email,
    ADD COLUMN email CHARACTER VARYING NOT NULL UNIQUE;