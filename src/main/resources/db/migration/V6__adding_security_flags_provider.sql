ALTER TABLE skip_diving.provider
    ADD COLUMN enabled BOOLEAN DEFAULT TRUE,
    ADD COLUMN authority CHARACTER VARYING DEFAULT 'provider'