package org.cryptonit.cloud.timestamping;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA384Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.operator.DigestCalculator;

/**
 * @author Mathias Brossard
 */
public class GenericDigestCalculator implements DigestCalculator {

    private ASN1ObjectIdentifier algo;
    private ByteArrayOutputStream bOut = new ByteArrayOutputStream();

    public GenericDigestCalculator(ASN1ObjectIdentifier algo) {
        this.algo = algo;
    }

    @Override
    public AlgorithmIdentifier getAlgorithmIdentifier() {
        return new AlgorithmIdentifier(this.algo);
    }

    @Override
    public OutputStream getOutputStream() {
        return bOut;
    }

    @Override
    public byte[] getDigest() {
        byte[] bytes = bOut.toByteArray();
        bOut.reset();

        Digest d;
        if (this.algo.equals(OIWObjectIdentifiers.idSHA1)) {
            d = new SHA1Digest();
        } else if (this.algo.equals(NISTObjectIdentifiers.id_sha256)) {
            d = new SHA256Digest();
        } else if (this.algo.equals(NISTObjectIdentifiers.id_sha384)) {
            d = new SHA384Digest();
        } else if (this.algo.equals(NISTObjectIdentifiers.id_sha512)) {
            d = new SHA512Digest();
        } else {
            return null;
        }
        d.update(bytes, 0, bytes.length);
        byte[] digest = new byte[d.getDigestSize()];
        d.doFinal(digest, 0);
        return digest;
    }
}
