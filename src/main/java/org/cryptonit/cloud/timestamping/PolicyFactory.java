package org.cryptonit.cloud.timestamping;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.cryptonit.cloud.ExecutionContext;
import org.cryptonit.cloud.interfaces.TimestampingPolicy;
import org.cryptonit.cloud.interfaces.TimestampingPolicyFactory;

public class PolicyFactory implements TimestampingPolicyFactory {
    ExecutionContext context;

    public PolicyFactory(ExecutionContext context) {
        this.context = context;
    }

    @Override
    public TimestampingPolicy getTimestampingPolicy(String domain) {
        return null;
    }

    @Override
    public TimestampingPolicy getTimestampingPolicy(String domain, ASN1ObjectIdentifier policyId) {
        return null;
    }    

    @Override
    public void addTimestampingPolicy(TimestampingPolicy policy, String domain) {
    }

    @Override
    public void addTimestampingPolicy(TimestampingPolicy policy, String domain, ASN1ObjectIdentifier policyId) {
    }
}
