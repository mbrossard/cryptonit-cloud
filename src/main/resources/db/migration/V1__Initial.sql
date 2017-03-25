CREATE TABLE keystore (
    id      serial,
    domain  varchar,
    keyId   varchar,
    type    varchar,
    created timestamp,
    public  text,
    private text
);
