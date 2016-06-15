package com.obsidian.octopus.vulcan.processor;

import com.google.inject.Injector;
import com.obsidian.octopus.vulcan.codec.RequestMessage;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author alex
 */
@Singleton
public class ProcessorFilter {

    private final List<ProcessorObject> mapping = new ArrayList<>();

    @Inject
    private Injector injector;

    public final void register(Object key, Class<? extends Processor> clazz) {
        ProcessorObject object = new ProcessorObject();
        object.setKey(key);
        object.setProcessor(clazz);
        mapping.add(object);
    }

    public void execute(IoSession session, RequestMessage requestMessage) throws Exception {
        Class clazz = requestMessage.getClass();
        for (ProcessorObject object : mapping) {
            if (object.getKey() == clazz) {
                Processor processor = injector.getInstance(object.getProcessor());
                processor.execute(session, requestMessage);
            }
        }
    }

    static class ProcessorObject {

        private Object key;
        private Class<? extends Processor> processor;

        public Object getKey() {
            return key;
        }

        public void setKey(Object key) {
            this.key = key;
        }

        public Class<? extends Processor> getProcessor() {
            return processor;
        }

        public void setProcessor(Class<? extends Processor> processor) {
            this.processor = processor;
        }

    }

}
