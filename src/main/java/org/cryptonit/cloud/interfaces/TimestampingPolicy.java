package org.cryptonit.cloud.interfaces;

/**
 * @author Mathias Brossard
 */
public interface TimestampingPolicy {

    String getPolicyId();

    String getIdentity();

    String getAlgorithm();
}
