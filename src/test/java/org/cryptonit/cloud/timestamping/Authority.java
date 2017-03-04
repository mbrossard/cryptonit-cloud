package org.cryptonit.cloud.timestamping;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

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
}
