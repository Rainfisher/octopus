package com.obsidian.octopus.context;

/**
 *
 * @author Alex Chou
 */
public class ContextProvider {

    private static Context context;

    public static Context setInstance(String[] args) {
        context = new ContextImpl(args);
        return context;
    }

    public static Context getInstance() {
        return context;
    }

}
