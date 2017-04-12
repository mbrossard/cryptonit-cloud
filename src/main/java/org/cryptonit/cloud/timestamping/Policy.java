package org.cryptonit.cloud.timestamping;

import org.cryptonit.cloud.interfaces.TimestampingPolicy;

public class Policy implements TimestampingPolicy {

    String identity;
    String policyId;

    public Policy(String identity, String policyId) {
        this.identity = identity;
        this.policyId = policyId;
    }

    @Override
    public String getIdentity() {
        return identity;
    }

    @Override
    public String getPolicyId() {
        return policyId;
    }
}
