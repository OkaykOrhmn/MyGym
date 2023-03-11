package com.example.mygym;

import java.util.Random;

public class Tools {
    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    public static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    public static String removeArray(String a) {
        a = a.replace("[", "");
        a = a.replace("]", "");
        a = a.replace(",", " ");

        return a;
    }

    public static String toArray(String a) {
        String res = "( ";
        res+=a;
        res+=" )";


        return res;
    }
}
