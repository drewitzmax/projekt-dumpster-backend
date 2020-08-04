CREATE TABLE skip_diving.provider(
    provider_id BIGSERIAL UNIQUE,
    name CHARACTER VARYING NOT NULL,
    address CHARACTER VARYING NOT NULL,
    email CHARACTER VARYING NOT NULL,
    password CHARACTER VARYING NOT NULL,
    phone_number CHARACTER VARYING NOT NULL,
    homepage CHARACTER VARYING
);

CREATE TABLE skip_diving.provider_image(
    provider_id BIGINT NOT NULL,
    photo_url CHARACTER VARYING NOT NULL,
    FOREIGN KEY (provider_id) REFERENCES skip_diving.provider(provider_id)
)