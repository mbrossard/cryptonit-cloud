package org.cryptonit.cloud.interfaces;

import javax.security.cert.X509Certificate;
import org.bouncycastle.asn1.x500.X500Name;

public interface IdentityStore {

    public String newIdentity(String domain, String keyId, X500Name subject) throws Exception;

    public boolean setCertificate(String domain, String identityId, X509Certificate certificate) throws Exception;
}
