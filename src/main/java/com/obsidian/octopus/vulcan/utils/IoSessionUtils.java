package com.obsidian.octopus.vulcan.utils;

import com.obsidian.octopus.vulcan.utils.ResponseUtils;
import com.obsidian.octopus.vulcan.codec.HttpResponseMessage;
import com.obsidian.octopus.vulcan.codec.IoSessionType;
import com.obsidian.octopus.vulcan.codec.ResponseMessage;
import com.obsidian.octopus.vulcan.object.ActionRequest;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author alex
 */
public class IoSessionUtils {

    public static WriteFuture write(IoSession ioSession, ActionRequest request, ResponseMessage response) {
        WriteFuture writeFuture = null;
        if (response != null) {
            IoSessionType type = getSessionType(ioSession);
            if (IoSessionType.Socket == type) {
                writeFuture = ioSession.write(response);
            } else if (IoSessionType.Http == type && !response.isPush()) {
                HttpResponseMessage httpResponse = ResponseUtils.getHttpResponse(response);
                writeFuture = ioSession.write(httpResponse);
            }
        }
        return writeFuture;
    }

    public static IoSessionType getSessionType(IoSession ioSession) {
        IoSessionType type = (IoSessionType) ioSession.getAttribute("type");
        return type == null ? IoSessionType.Null : type;
    }

}
