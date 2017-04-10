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
    identityId  varchar,
    domain      varchar,
    keyId       varchar,
    subject     varchar,
    created     timestamp,
    request     text,
    certificate text
);

CREATE TABLE timestamping_policy (
    id          serial,
    domain      varchar,
    policyId    varchar,
    identityId  varchar,
    created     timestamp
);
