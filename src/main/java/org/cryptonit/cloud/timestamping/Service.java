package org.cryptonit.cloud.timestamping;

import org.cryptonit.cloud.ExecutionContext;

public class Service {
    public static void register(ExecutionContext context) {
        org.cryptonit.cloud.timestamping.AuthorityFactory tsaFactory = new AuthorityFactory(context);
        Application.setTimestampingAuthorityFactory(tsaFactory);
        org.cryptonit.cloud.Application.addClass(Application.class);
    }
}
