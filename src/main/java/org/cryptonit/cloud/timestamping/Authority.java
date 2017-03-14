package org.cryptonit.cloud.timestamping;

import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import org.bouncycastle.tsp.*;
import org.bouncycastle.util.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mathias Brossard
 */
public class Authority {
    private final BigInteger one = new BigInteger("1");
    private static Logger LOGGER;
    KeyPair key;
    X509Certificate crt;
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

    public Authority(KeyPair key, X509Certificate crt, Store certs) {
        LOGGER = LoggerFactory.getLogger(Authority.class);
        
        this.key = key;
        this.crt = crt;
        this.certs = certs;
        this.serial = new BigInteger("1");
    }
}
