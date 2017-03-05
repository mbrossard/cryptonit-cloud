package org.cryptonit.cloud.timestamping;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Date;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.x509.X509V3CertificateGenerator;

/**
 * @author Mathias Brossard
 */
public class Authority {
    KeyPair generateKeypair(int size) {
        SecureRandom rand = new SecureRandom();
        KeyPairGenerator pairGenerator = null;
        try {
            pairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
            pairGenerator.initialize(size, rand);
            KeyPair kp = pairGenerator.generateKeyPair();
        return kp;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(System.err);
        } catch (NoSuchProviderException e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    public static X509Certificate makeCertificate(KeyPair subjectKey, X509Name subjectDN,
                                                  KeyPair issuerKey, X509Name issuerDN,
                                                  BigInteger serial, boolean isCA)
    {
        JcaX509ExtensionUtils extUtils = null;
        try {
            extUtils= new JcaX509ExtensionUtils();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(System.err);
        }

        PublicKey subjectPublicKey = subjectKey.getPublic();
        PrivateKey issuerPrivateKey = issuerKey.getPrivate();
        PublicKey issuerPublicKey = issuerKey.getPublic();

        X509V3CertificateGenerator crtGen = new X509V3CertificateGenerator();

        crtGen.reset();
        crtGen.setSerialNumber(serial);
        crtGen.setIssuerDN(issuerDN);
        crtGen.setNotBefore(new Date(System.currentTimeMillis()));
        crtGen.setNotAfter(new Date(System.currentTimeMillis()
                + (1000 * 86400 * 365)));
        crtGen.setSubjectDN(subjectDN);
        crtGen.setPublicKey(subjectPublicKey);
        crtGen.setSignatureAlgorithm("Sha256WithRSAEncryption");

        if(extUtils != null) {
            crtGen.addExtension(Extension.subjectKeyIdentifier, false,
                    extUtils.createSubjectKeyIdentifier(subjectPublicKey));

            crtGen.addExtension(Extension.authorityKeyIdentifier, false,
                    extUtils.createAuthorityKeyIdentifier(issuerPublicKey));
        }

        if (isCA) {
            crtGen.addExtension(Extension.basicConstraints, false,
                    new BasicConstraints(isCA));
        } else {
            crtGen.addExtension(Extension.extendedKeyUsage, true,
                    new ExtendedKeyUsage(KeyPurposeId.id_kp_timeStamping));
        }

        X509Certificate crt = null;
        try {
            crt = crtGen.generate(issuerPrivateKey);
            crt.checkValidity(new Date());
            crt.verify(issuerPublicKey);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return crt;
    }
}
