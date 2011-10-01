package org.ritsuka.youji.util;

/**
 * Date: 10/1/11
 * Time: 2:30 PM
 */
public class StaticConfig {
    private static StaticConfig ourInstance = new StaticConfig();

    public static StaticConfig getInstance() {
        return ourInstance;
    }

    private StaticConfig() {
    }
}
