ALTER TABLE skip_diving.sd_user
    ADD COLUMN enabled BOOLEAN DEFAULT TRUE,
    ADD COLUMN authority CHARACTER VARYING DEFAULT 'user'