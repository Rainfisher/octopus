package com.obsidian.octopus.context;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class ContextProvider {

    private static final Context context = new ContextImpl();
    private static Boolean register = false;

    public static Context getInstance() {
        if (!register) {
            Thread thread = new Thread(context);
            Runtime.getRuntime().addShutdownHook(thread);
            register = true;
        }
        return context;
    }

}
