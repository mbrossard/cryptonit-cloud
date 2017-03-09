package org.cryptonit.cloud.timestamping;

import org.bouncycastle.util.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.*;
import java.security.cert.X509Certificate;
/**
 * @author Mathias Brossard
 */
public class Authority {
    private static Logger LOGGER;
        
    public Authority(KeyPair key, X509Certificate crt, Store certs) {
        LOGGER = LoggerFactory.getLogger(Authority.class);
    }
}
