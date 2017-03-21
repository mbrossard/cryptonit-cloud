package org.cryptonit.cloud.timestamping;

import org.cryptonit.cloud.ExecutionContext;
import org.cryptonit.cloud.interfaces.TimestampingAuthority;
import org.cryptonit.cloud.interfaces.TimestampingAuthorityFactory;

public class AuthorityFactory implements TimestampingAuthorityFactory {
    ExecutionContext context;

    public AuthorityFactory(ExecutionContext context) {
        this.context = context;
    }

    @Override
    public TimestampingAuthority getTimestampingAuthority(String domain) {
        return null;
    }
}
