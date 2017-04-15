package by.galkina.game.logic;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryptor {
    private static final Logger LOG = LogManager.getLogger(Encryptor.class);

    public static String encipherPassword(String password) {
        MessageDigest messageDigest = null;
        byte bytesEncoded[] = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(password.getBytes("utf8"));
            bytesEncoded = messageDigest.digest();
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            LOG.error(e);
        }
        BigInteger bigInt = new BigInteger(1, bytesEncoded);
        return bigInt.toString(16);
    }
}
