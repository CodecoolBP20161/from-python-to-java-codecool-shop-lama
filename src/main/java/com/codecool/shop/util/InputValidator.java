package com.codecool.shop.util;

public class InputValidator {

    public static boolean isAlpha(String str) {
        for (String partStr : str.split(" ")) {
            char[] chars = partStr.toCharArray();

            for (char c : chars) {
                if (!Character.isLetter(c)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isEmail(String str){
        if ((!str.contains(" ")) && str.contains("@")) {
            String[] parts = str.split("@");
            if (parts.length == 2) {
                String[] parts2 = parts[1].split("\\.");
                if (parts2.length >= 2) {
                    return isAlpha(parts2[parts2.length-1]);
                }
            }
        }
        return false;
    }

    public static boolean isPhoneNumber(String str){
        if (str.charAt(0) == '+'){
            return isNumber(str.substring(1));
        }
        return false;
    }

    public static boolean isNumber(String str){
        char[] chars = str.toCharArray();

        for (char c : chars) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

//    USE FOR TEST:
//    public static void main(String[] args){
//        String asd = "+1";
//        System.out.println(isAlpha(asd));
//        System.out.println(isEmail(asd));
//        System.out.println(isPhoneNumber(asd));
//    }

}
