package com.sakti.toko.common.extension;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AESOperation {
    private static final String SALT = "thesalt";

    public static String encode(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String saltedPass = password + SALT;
            byte[] hash = md.digest(saltedPass.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e ) {
            throw new RuntimeException( "Jir Eror", e);
        }
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}