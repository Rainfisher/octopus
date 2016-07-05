package com.obsidian.octopus.vulcan.filter;

import org.apache.mina.core.session.IoSession;

/**
 *
 * @author alex
 * @param <E>
 */
public interface HandlerEvent<E> {

    void trigger(IoSession ioSession, E e) throws Exception;

}
