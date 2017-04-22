package org.cryptonit.cloud.timestamping;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.tsp.*;
import org.bouncycastle.util.Store;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Mathias Brossard
 */
public class AuthorityTest {
    KeyPair generateKeypair(int size) throws Exception {
        SecureRandom rand = new SecureRandom();
        KeyPairGenerator pairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
        pairGenerator.initialize(size, rand);
        KeyPair kp = pairGenerator.generateKeyPair();
        return kp;
    }

    public static X509Certificate makeCertificate(KeyPair subjectKey, X500Name subjectDN,
                                                  KeyPair issuerKey, X500Name issuerDN,
                                                  BigInteger serial, boolean isCA)
            throws Exception
    {
        JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();
        PublicKey subjectPublicKey = subjectKey.getPublic();
        PrivateKey issuerPrivateKey = issuerKey.getPrivate();
        PublicKey issuerPublicKey = issuerKey.getPublic();
        SubjectPublicKeyInfo keyInfo = SubjectPublicKeyInfo.getInstance(subjectPublicKey.getEncoded());
        Date notBefore = new Date(System.currentTimeMillis());
        Date notAfter = new Date(System.currentTimeMillis() + (1000 * 86400 * 365));
        X509v3CertificateBuilder crtBuild = new X509v3CertificateBuilder(issuerDN, serial, notBefore, notAfter, subjectDN, keyInfo);

        if (extUtils != null) {
            crtBuild.addExtension(Extension.subjectKeyIdentifier, false,
                    extUtils.createSubjectKeyIdentifier(subjectPublicKey));

            crtBuild.addExtension(Extension.authorityKeyIdentifier, false,
                    extUtils.createAuthorityKeyIdentifier(issuerPublicKey));
        }

        if (isCA) {
            crtBuild.addExtension(Extension.basicConstraints, false,
                    new BasicConstraints(isCA));
        } else {
            crtBuild.addExtension(Extension.extendedKeyUsage, true,
                    new ExtendedKeyUsage(KeyPurposeId.id_kp_timeStamping));
        }

        ContentSigner cs = new JcaContentSignerBuilder("SHA256withRSA").build(issuerPrivateKey);
        X509Certificate crt = new JcaX509CertificateConverter().getCertificate(crtBuild.build(cs));
        crt.checkValidity(new Date());
        crt.verify(issuerPublicKey);

        return crt;
    }
    
    @Test
    public void tsaTest() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        X500Name caDN = new X500Name("CN=CA, O=Test");
        X500Name tsaDN = new X500Name("CN=TSA, O=Test");
        KeyPair caKey = this.generateKeypair(2048);
        KeyPair tsaKey = this.generateKeypair(2048);
        BigInteger serialNumber = new BigInteger("1");
        X509Certificate caCRT = makeCertificate(caKey, caDN, caKey, caDN, serialNumber, true);
        serialNumber = serialNumber.add(new BigInteger("1"));
        X509Certificate tsaCRT = makeCertificate(tsaKey, caDN, caKey, tsaDN, serialNumber, false);


        List<X509Certificate> certList = new ArrayList<>();
        certList.add(caCRT);
        certList.add(tsaCRT);

        Store certs = new JcaCertStore(certList);

        Authority tsa = new Authority(tsaKey, tsaCRT, certs, new ASN1ObjectIdentifier("1.2.3.4"));

        TimeStampRequestGenerator reqGen = new TimeStampRequestGenerator();
        TimeStampRequest request = reqGen.generate(TSPAlgorithms.SHA1, new byte[20]);
        TimeStampResponse response = tsa.timestamp(request);

        response = new TimeStampResponse(response.getEncoded());
        TimeStampToken token = response.getTimeStampToken();
        token.validate(new JcaSimpleSignerInfoVerifierBuilder().setProvider(BouncyCastleProvider.PROVIDER_NAME).build(tsaCRT));

        Assert.assertEquals(1, 1);
    }
}
