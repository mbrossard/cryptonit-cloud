package org.cryptonit.cloud.interfaces;

/**
 * @author Mathias Brossard
 */
import org.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface TimestampingPolicyFactory {

    TimestampingPolicy getTimestampingPolicy(String domain);

    TimestampingPolicy getTimestampingPolicy(String domain, ASN1ObjectIdentifier policyId);

    void addTimestampingPolicy(TimestampingPolicy policy, String domain) throws Exception;

    void addTimestampingPolicy(TimestampingPolicy policy, String domain, ASN1ObjectIdentifier policyId) throws Exception;
}
