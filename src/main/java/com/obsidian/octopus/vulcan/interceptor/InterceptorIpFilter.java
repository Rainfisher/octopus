package com.obsidian.octopus.vulcan.interceptor;

import com.obsidian.octopus.utils.IpUtils;
import com.obsidian.octopus.vulcan.codec.HttpResponseMessage;
import com.obsidian.octopus.vulcan.object.ActionContext;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;

/**
 *
 * @author alex
 */
@Singleton
public class InterceptorIpFilter implements Interceptor {

    private final List<IpObject> list = new ArrayList<>();

    public void clear() {
        list.clear();
    }

    public void addIptables(long ipStart, long ipEnd) {
        IpObject object = new IpObject();
        object.ipStart = ipStart;
        object.ipEnd = ipEnd;
        list.add(object);
    }

    @Override
    public boolean intercept(ActionInvocation invocation) throws Exception {
        String ip = IpUtils.getIpAddr();
        long ipNumber = IpUtils.ip2Long(ip);
        boolean ret = false;
        for (IpObject ipObject : list) {
            Long key = ipObject.ipStart;
            Long value = ipObject.ipEnd;
            if (ipNumber >= key && ipNumber <= value) {
                ret = true;
                break;
            }
        }
        if (!ret) {
            ActionContext.set(ActionContext.HTTP_RESPONSE_CODE, HttpResponseMessage.HTTP_STATUS_FORBIDDEN);
        }
        return ret;
    }

    private class IpObject {

        long ipStart;
        long ipEnd;
    }

}
