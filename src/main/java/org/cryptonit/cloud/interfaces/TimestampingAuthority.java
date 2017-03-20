package org.cryptonit.cloud.interfaces;

import org.bouncycastle.tsp.*;

public interface TimestampingAuthority {
    public TimeStampResponse timestamp(TimeStampRequest request) throws TSPException;
}
