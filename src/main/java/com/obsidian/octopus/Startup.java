package com.obsidian.octopus;

import com.obsidian.octopus.dispatcher.Dispatcher;
import com.obsidian.octopus.xml.XmlResolver;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class Startup {

    public static void main(String[] args) throws Exception {
        String moduleName = null;
        if (args.length > 0) {
            moduleName = args[0];
        }
        XmlResolver resolver = new XmlResolver(moduleName);
        Dispatcher dispatcher = Dispatcher.createDispatcher();
        dispatcher.start(resolver.build());
    }

}
