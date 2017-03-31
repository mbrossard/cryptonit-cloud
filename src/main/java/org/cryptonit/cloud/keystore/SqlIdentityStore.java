package org.cryptonit.cloud.keystore;

import org.bouncycastle.asn1.x500.X500Name;
import org.cryptonit.cloud.Database;
import org.cryptonit.cloud.interfaces.IdentityStore;
import org.cryptonit.cloud.interfaces.KeyStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlIdentityStore implements IdentityStore {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlIdentityStore.class);
    Database database;
    KeyStore keyStore;

    public SqlIdentityStore(Database database, KeyStore keyStore) {
        this.database = database;
        this.keyStore = keyStore;
    }

    @Override
    public String newIdentity(String domain, String keyId, X500Name subject) throws Exception {
        PrivateKey key = keyStore.getPrivateKey(domain, keyId);
        PublicKey pub = keyStore.getPublicKey(domain, keyId);
        ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA").build(key);
        JcaPKCS10CertificationRequestBuilder csrBuilder = new JcaPKCS10CertificationRequestBuilder(subject, pub);
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(System.currentTimeMillis()));
        csrBuilder.addAttribute(PKCSObjectIdentifiers.pkcs_9_at_challengePassword, new DERPrintableString(date));
        PKCS10CertificationRequest csr = csrBuilder.build(signer);

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(csr.getEncoded());
        byte[] digest = md.digest();

        String id = String.format("%064x", new java.math.BigInteger(1, digest));
        return id;
    }    
}
