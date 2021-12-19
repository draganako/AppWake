package com.example.appwake.SHA512;


import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Sha512 {

    public static byte[] Salt() {
        SecureRandom random = new SecureRandom();
        byte salt[] = new byte[8];
        random.nextBytes(salt);
        return salt;
    }

    public static String encryptThisString(String input, String salt)
    {

        try {
            // getInstance() method is called with algorithm SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] b = salt.getBytes(StandardCharsets.ISO_8859_1);
            byte[] a = input.getBytes(StandardCharsets.ISO_8859_1);

            byte[] concat = new byte[a.length + b.length];
            System.arraycopy(a, 0, concat, 0, a.length);
            System.arraycopy(b, 0, concat, a.length, b.length);

            byte[] messageDigest = md.digest(concat);


            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }


            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


}


