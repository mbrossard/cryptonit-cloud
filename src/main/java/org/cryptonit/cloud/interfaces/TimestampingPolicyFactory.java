package org.cryptonit.cloud.interfaces;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface TimestampingPolicyFactory {

    TimestampingPolicy getTimestampingPolicy(String domain);

    TimestampingPolicy getTimestampingPolicy(String domain, ASN1ObjectIdentifier policyId);
}
