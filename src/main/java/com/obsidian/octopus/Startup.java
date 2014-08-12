package com.obsidian.octopus;

import com.obsidian.octopus.dispatcher.Dispatcher;
import com.obsidian.octopus.xml.XmlConfigurationProvider;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class Startup {

    public static void main(String[] args) throws Exception {
        String moduleName = null;
        if (args != null && args.length > 0) {
            moduleName = args[0];
        }
        XmlConfigurationProvider resolver = new XmlConfigurationProvider(moduleName);
        Dispatcher dispatcher = Dispatcher.createDispatcher();
        dispatcher.start(resolver.build());
    }

}
