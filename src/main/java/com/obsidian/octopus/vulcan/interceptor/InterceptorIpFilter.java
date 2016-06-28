package com.obsidian.octopus.vulcan.interceptor;

import com.obsidian.octopus.utils.IpUtils;
import com.obsidian.octopus.vulcan.codec.HttpResponseMessage;
import com.obsidian.octopus.vulcan.object.ActionContext;
import java.util.ArrayList;
import java.util.Iterator;
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
        synchronized (list) {
            list.clear();
        }
    }

    public void addIptables(long ipStart, long ipEnd) {
        IpObject object = new IpObject();
        object.ipStart = ipStart;
        object.ipEnd = ipEnd;
        synchronized (list) {
            list.add(object);
        }
    }

    public void deleteOne(long ipStart, long ipEnd) {
        synchronized (list) {
            for (Iterator<IpObject> iterator = list.iterator(); iterator.hasNext();) {
                IpObject ipObject = iterator.next();
                if (ipObject.ipStart == ipStart && ipObject.ipEnd == ipEnd) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    @Override
    public boolean intercept(ActionInvocation invocation) throws Exception {
        String ip = IpUtils.getIpAddr();
        long ipNumber = IpUtils.ip2Long(ip);
        boolean ret = false;
        synchronized (list) {
            for (IpObject ipObject : list) {
                Long key = ipObject.ipStart;
                Long value = ipObject.ipEnd;
                if (ipNumber >= key && ipNumber <= value) {
                    ret = true;
                    break;
                }
            }
        }
        if (ret) {
            invocation.invoke();
        } else {
            ActionContext.set(ActionContext.HTTP_RESPONSE_CODE, HttpResponseMessage.HTTP_STATUS_FORBIDDEN);
        }
        return ret;
    }

    private class IpObject {

        long ipStart;
        long ipEnd;
    }

}
