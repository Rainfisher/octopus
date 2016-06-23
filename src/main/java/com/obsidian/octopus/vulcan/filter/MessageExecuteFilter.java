package com.obsidian.octopus.vulcan.filter;

import com.obsidian.octopus.vulcan.utils.IoSessionUtils;
import com.obsidian.octopus.vulcan.codec.RequestMessage;
import com.obsidian.octopus.vulcan.codec.ResponseMessage;
import com.obsidian.octopus.vulcan.object.ActionContext;
import com.obsidian.octopus.vulcan.object.ActionRequest;
import com.obsidian.octopus.vulcan.processor.ProcessorFilter;
import javax.inject.Inject;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author Alex Chou
 */
public class MessageExecuteFilter {

    @Inject
    private ProcessorFilter processorFilter;

    public void execute(IoSession ioSession, RequestMessage requestMessage)
            throws Exception {
        requestMessage.setExecuteAt(System.currentTimeMillis());
        ActionContext.init();
        ActionContext.set(ActionContext.IO_SESSION, ioSession);
        ActionContext.set(ActionContext.REQUEST_MESSAGE, requestMessage);

        processorFilter.execute(ioSession, requestMessage);
        ActionRequest actionRequest = ActionContext.getActionRequest();
        Object response = ActionContext.get(ActionContext.RESPONSE);
        if (response != null && response instanceof ResponseMessage) {
            IoSessionUtils.write(ioSession, actionRequest, (ResponseMessage) response);
        }
    }

    public void clearActionContext() {
        ActionContext.removeActionContext();
    }

}
