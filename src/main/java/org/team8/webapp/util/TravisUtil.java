package org.team8.webapp.util;

public class TravisUtil {
    private static Boolean travis;

    public static boolean isTravis() {
        if(travis == null)
            travis = System.getenv("TRAVIS") != null;

        return travis;
    }
}