CREATE TABLE keystore (
    id      serial,
    domain  varchar,
    keyId   varchar,
    type    varchar,
    created timestamp,
    public  text,
    private text
);

CREATE TABLE identity (
    id          serial,
    signerId    varchar,
    domain      varchar,
    keyId       varchar,
    subject     varchar,
    created     timestamp,
    request     text,
    certificate text
);
