package uk.co.ivandimitrov.jhibp;

import uk.co.ivandimitrov.jhibp.service.HibpPasswordService;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hibp {

    public static boolean password(final String password) throws IOException, InterruptedException, NoSuchAlgorithmException {
        final var encryptedString = encryptInput(password).toUpperCase();
        final var response = HibpPasswordService.getInstance().callHibpPasswordApi(encryptedString.substring(0, 5));
        return response.body().contains(encryptedString.substring(5));
    }

    private static String encryptInput(final String input) throws NoSuchAlgorithmException {
        final var digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(input.getBytes(StandardCharsets.UTF_8));
        return String.format("%040x", new BigInteger(1, digest.digest()));
    }

}
