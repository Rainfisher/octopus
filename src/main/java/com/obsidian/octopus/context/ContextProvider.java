package com.obsidian.octopus.context;

/**
 *
 * @author Alex Chou
 */
public class ContextProvider {

    private static final Context context = new ContextImpl();

    public static Context getInstance() {
        return context;
    }

}
