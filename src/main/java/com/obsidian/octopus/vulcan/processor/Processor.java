package com.obsidian.octopus.vulcan.processor;

import com.obsidian.octopus.vulcan.codec.RequestMessage;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author alex
 */
public interface Processor {
    
    void execute(IoSession session, RequestMessage requestMessage) throws Exception;
    
}
