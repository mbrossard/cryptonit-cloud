package org.cryptonit.cloud.timestamping;

import org.cryptonit.cloud.interfaces.TimestampingPolicy;

public class Policy implements TimestampingPolicy {

    String identity;
    String policyId;
    String algorithm;

    public Policy(String identity, String policyId, String algorithm) {
        this.identity = identity;
        this.policyId = policyId;
        this.algorithm = algorithm;
    }

    @Override
    public String getIdentity() {
        return identity;
    }

    @Override
    public String getPolicyId() {
        return policyId;
    }

    public String getAlgorithm() {
        return algorithm;
    }
}
