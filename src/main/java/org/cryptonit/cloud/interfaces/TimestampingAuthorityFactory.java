package org.cryptonit.cloud.interfaces;

public interface TimestampingAuthorityFactory {
    TimestampingAuthority getTimestampingAuthority(String domain);
}
