package com.codecool.shop.util;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class passwordHasher {
    private static char[] saltChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static String generateSalt(){
        String salt = "";
        SecureRandom rnd = new SecureRandom();
        for (int i=0; i<64; i++){
            salt += saltChars[rnd.nextInt(saltChars.length)];
        }
        return salt;
    }
}
