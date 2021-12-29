package es.uco.ism.system796.Seguridad;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Cipher {

    public Cipher() {
    }

    public String sha1(String stringToHash) {
        String sha1 = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(stringToHash.getBytes("utf8"));
            sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sha1;
    }
}
