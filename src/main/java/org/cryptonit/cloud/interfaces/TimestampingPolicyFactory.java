package org.cryptonit.cloud.interfaces;

public interface TimestampingPolicyFactory {

    TimestampingPolicy getTimestampingPolicy(String domain);
}
