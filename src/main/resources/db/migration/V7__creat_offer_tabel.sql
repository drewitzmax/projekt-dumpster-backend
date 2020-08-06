CREATE TABLE skip_diving.offer(
    offer_id BIGSERIAL UNIQUE ,
    title CHARACTER VARYING,
    description CHARACTER VARYING,
    amount_offered INTEGER,
    amount_remaining INTEGER
);

CREATE TABLE skip_diving.provider_offer(
    offer_id BIGINT,
    provider_id BIGINT,
    FOREIGN KEY ("offer_id") REFERENCES skip_diving.offer("offer_id"),
    FOREIGN KEY ("provider_id") REFERENCES skip_diving.provider("provider_id")
);

CREATE TABLE skip_diving.offer_user(
    offer_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY ("offer_id") REFERENCES skip_diving.offer("offer_id"),
    FOREIGN KEY ("user_id") REFERENCES skip_diving.sd_user("user_id")
);