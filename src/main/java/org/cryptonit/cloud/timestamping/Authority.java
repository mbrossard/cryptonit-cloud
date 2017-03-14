package org.cryptonit.cloud.timestamping;

import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
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
    private static Logger LOGGER;
    ASN1ObjectIdentifier policy;
    X509Certificate crt;
    KeyPair key;
    Store certs;
    BigInteger serial;

    synchronized private BigInteger getNextSerial() {
        BigInteger s = serial;
        serial.add(one);
        return s;
    }

    public TimeStampResponse timestamp(TimeStampRequest request) {
        TimeStampResponse response = null;
        return response;
    }

    public Authority(KeyPair key, X509Certificate crt, Store certs, ASN1ObjectIdentifier policy) {
        LOGGER = LoggerFactory.getLogger(Authority.class);
        
        this.key = key;
        this.crt = crt;
        this.certs = certs;
        this.policy = policy;
        this.serial = new BigInteger("1");
    }
}
