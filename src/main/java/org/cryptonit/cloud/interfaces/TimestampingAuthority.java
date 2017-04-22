package org.cryptonit.cloud.interfaces;

import org.bouncycastle.tsp.*;

/**
 * @author Mathias Brossard
 */
public interface TimestampingAuthority {

    public TimeStampResponse timestamp(TimeStampRequest request) throws TSPException;
}
