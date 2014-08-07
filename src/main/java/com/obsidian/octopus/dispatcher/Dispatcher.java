package com.obsidian.octopus.dispatcher;

import com.obsidian.octopus.resolver.Resolver;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public abstract class Dispatcher {

    public static Dispatcher createDispatcher() {
        return new DispatcherImpl();
    }

    public abstract void start(Resolver resolver);

    static class DispatcherImpl extends Dispatcher {

        @Override
        public void start(Resolver resolver) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }
}
