package org.cryptonit.cloud.interfaces;

import org.bouncycastle.asn1.x500.X500Name;

public interface IdentityStore {
    public String newIdentity(String domain, String keyId, X500Name subject) throws Exception;
}
