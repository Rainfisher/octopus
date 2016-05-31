package com.obsidian.octopus;

import com.obsidian.octopus.dispatcher.Dispatcher;
import com.obsidian.octopus.xml.XmlConfigurationProvider;

/**
 *
 * @author Alex Chou
 */
public class Startup {

    public static void main(String[] args) throws Exception {
        String moduleName = null;
        if (args != null && args.length > 0) {
            moduleName = args[0];
        }
        biu(moduleName);
    }

    public static void biu(String moduleName) throws Exception {
        XmlConfigurationProvider resolver = new XmlConfigurationProvider(moduleName);
        Dispatcher dispatcher = Dispatcher.createDispatcher();
        dispatcher.start(resolver.build());
    }

}
