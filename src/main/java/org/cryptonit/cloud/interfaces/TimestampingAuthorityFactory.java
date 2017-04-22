package org.cryptonit.cloud.interfaces;

/**
 * @author Mathias Brossard
 */
public interface TimestampingAuthorityFactory {

    TimestampingAuthority getTimestampingAuthority(String domain);
}
