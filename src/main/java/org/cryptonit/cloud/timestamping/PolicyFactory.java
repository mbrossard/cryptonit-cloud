package org.cryptonit.cloud.timestamping;

import java.sql.CallableStatement;
import java.sql.Connection;
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
    public void addTimestampingPolicy(TimestampingPolicy policy, String domain) throws Exception {
        Connection c = context.database.getConnection();
        CallableStatement cs = c.prepareCall("INSERT INTO timestamping_policy(domain, policyId, identityId, algorithm, created) "
                + "VALUES (?, NULL, ?, ?, NOW())");
        cs.setString(1, domain);
        cs.setString(2, policy.getIdentity());
        cs.setString(3, policy.getAlgorithm());
        cs.execute();
    }

    @Override
    public void addTimestampingPolicy(TimestampingPolicy policy, String domain, ASN1ObjectIdentifier policyId) throws Exception {
        Connection c = context.database.getConnection();
        CallableStatement cs = c.prepareCall("INSERT INTO timestamping_policy(domain, policyId, identityId, algorithm, created) "
                + "VALUES (?, ?, ?, ?, NOW())");
        cs.setString(1, domain);
        cs.setString(2, policyId.toString());
        cs.setString(3, policy.getIdentity());
        cs.setString(4, policy.getAlgorithm());
        cs.execute();
    }
}
