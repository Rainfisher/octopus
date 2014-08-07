package com.obsidian.octopus;

import com.obsidian.octopus.dispatcher.Dispatcher;
import com.obsidian.octopus.xml.XmlBuilder;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class Startup {

    public static void main(String[] args) throws Exception {
        String startupName = null;
        if (args.length > 0) {
            startupName = args[0];
        }
        XmlBuilder builder = new XmlBuilder(startupName);
        Dispatcher dispatcher = builder.build();
//        dispatcher.start();
    }

}
