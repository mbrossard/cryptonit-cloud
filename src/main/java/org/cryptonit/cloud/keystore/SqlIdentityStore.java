package org.cryptonit.cloud.keystore;

import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import javax.security.cert.X509Certificate;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
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

        Connection c = database.getConnection();
        CallableStatement cs = c.prepareCall("INSERT INTO identity(domain, identityId, subject, created, request) "
                + "VALUES (?, ?, ?, NOW(), ?)");
        cs.setString(1, domain);
        cs.setString(2, id);
        cs.setString(3, subject.toString());
        cs.setString(4, Base64.getEncoder().encodeToString(csr.getEncoded()));
        cs.execute();

        return id;
    }

    @Override
    public boolean setCertificate(String domain, String identityId, X509Certificate certificate) throws Exception {
        Connection c = database.getConnection();

        PreparedStatement ps = c.prepareStatement("SELECT request FROM keystore WHERE domain=? and identityId=?");
        ps.setString(1, domain);
        ps.setString(2, identityId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            PKCS10CertificationRequest csr = new PKCS10CertificationRequest(Base64.getDecoder().decode(rs.getString(1)));
        }

        CallableStatement cs = c.prepareCall("UPDATE identity SET certificate=? WHERE domain=? AND identityId=?");
        cs.setString(1, Base64.getEncoder().encodeToString(certificate.getEncoded()));
        cs.setString(2, domain);
        cs.setString(3, identityId);
        cs.execute();

        return true;
    }

    @Override
    public PKCS10CertificationRequest getRequest(String domain, String identityId) throws Exception {
        PKCS10CertificationRequest csr = null;
        Connection c = database.getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT request FROM identity WHERE domain=? and identityId=?");
        ps.setString(1, domain);
        ps.setString(2, identityId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            csr = new PKCS10CertificationRequest(Base64.getDecoder().decode(rs.getString(1)));
        }

        return csr;
    }

    @Override
    public X509Certificate getCertificate(String domain, String identityId) throws Exception {
        X509Certificate crt = null;

        return crt;
    }

    @Override
    public PrivateKey getKey(String domain, String identityId) throws Exception {
        PrivateKey key = null;

        return key;
    }
}
