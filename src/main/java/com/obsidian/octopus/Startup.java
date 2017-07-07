package com.obsidian.octopus;

import com.obsidian.octopus.dispatcher.Dispatcher;
import com.obsidian.octopus.resolver.Resolver;
import com.obsidian.octopus.xml.XmlConfigurationProvider;
import java.util.Arrays;

/**
 *
 * @author Alex Chou
 */
public class Startup {

    public static void main(String[] args) throws Exception {
        String moduleName = null;
        String[] leftArgs = new String[0];
        if (args != null && args.length > 0) {
            moduleName = args[0];
            if (args.length > 1) {
                leftArgs = Arrays.copyOfRange(args, 1, args.length);
            }
        }
        biu(moduleName, leftArgs);
    }

    public static void biu(String moduleName, String[] args) throws Exception {
        XmlConfigurationProvider resolver = new XmlConfigurationProvider(moduleName);
        Dispatcher dispatcher = Dispatcher.createDispatcher();
        Resolver res = resolver.build();
        res.setArgs(args);
        dispatcher.start(res);
    }

}
