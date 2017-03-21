package org.cryptonit.cloud.timestamping;

import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoGeneratorBuilder;
import org.bouncycastle.tsp.*;
import org.bouncycastle.util.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mathias Brossard
 */
public class Authority {
    public static final Set ALLOWED = new HashSet(Arrays.asList(new ASN1ObjectIdentifier[] {
        TSPAlgorithms.SHA1, TSPAlgorithms.SHA256, TSPAlgorithms.SHA384, TSPAlgorithms.SHA512
    }));
    private final BigInteger one = new BigInteger("1");
    private static Logger LOGGER = LoggerFactory.getLogger(Authority.class);
    TimeStampResponseGenerator generator;
    BigInteger serial;

    public Authority(KeyPair key, X509Certificate crt, Store certs, ASN1ObjectIdentifier policy) {
        TimeStampTokenGenerator tokenGen = null;
        try {
            JcaSimpleSignerInfoGeneratorBuilder builder = new JcaSimpleSignerInfoGeneratorBuilder();
            SignerInfoGenerator signer = builder.build("SHA256withRSA", key.getPrivate(), crt);
            tokenGen = new TimeStampTokenGenerator(signer, new GenericDigestCalculator(NISTObjectIdentifiers.id_sha256), policy);
            tokenGen.addCertificates(certs);
        } catch (Exception e) {
            LOGGER.error("Error instantiating Timestamping authority", e);
        }

        generator = new TimeStampResponseGenerator(tokenGen, ALLOWED);
        serial = new BigInteger("1");
    }

    synchronized private BigInteger getNextSerial() {
        BigInteger s = serial;
        serial.add(one);
        return s;
    }

    public TimeStampResponse timestamp(TimeStampRequest request) throws TSPException {
        return generator.generate(request, getNextSerial(), new Date());
    }
}
