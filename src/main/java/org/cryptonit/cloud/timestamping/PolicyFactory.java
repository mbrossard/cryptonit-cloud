package org.cryptonit.cloud.timestamping;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        TimestampingPolicy r = null;

        try {
            Connection c = context.database.getConnection();
            PreparedStatement ps = c.prepareStatement("SELECT identityId, algorithm FROM timestamping_policy WHERE domain=? and policyId is NULL");
            ps.setString(1, domain);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                r = new Policy(rs.getString(1), null, rs.getString(2));
            }
        } catch (Exception e) {
            // TODO: Log error
        }

        return r;
    }

    @Override
    public TimestampingPolicy getTimestampingPolicy(String domain, ASN1ObjectIdentifier policyId) {
        TimestampingPolicy r = null;

        try {
            Connection c = context.database.getConnection();
            PreparedStatement ps = c.prepareStatement("SELECT identityId, policyId, algorithm FROM timestamping_policy WHERE domain=? and policyId=?");
            ps.setString(1, domain);
            ps.setString(2, policyId.toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                r = new Policy(rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (Exception e) {
            // TODO: Log error
        }

        return r;
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
