package org.cryptonit.cloud.interfaces;

import java.security.PrivateKey;
import java.security.PublicKey;
import javax.security.cert.X509Certificate;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

/**
 * @author Mathias Brossard
 */
public interface IdentityStore {

    public String newIdentity(String domain, String keyId, X500Name subject) throws Exception;

    public boolean setCertificate(String domain, String identityId, X509Certificate certificate) throws Exception;

    public PKCS10CertificationRequest getRequest(String domain, String identityId) throws Exception;

    public X509Certificate getCertificate(String domain, String identityId) throws Exception;

    public PrivateKey getPrivateKey(String domain, String identityId) throws Exception;

    public PublicKey getPublicKey(String domain, String identityId) throws Exception;
}
