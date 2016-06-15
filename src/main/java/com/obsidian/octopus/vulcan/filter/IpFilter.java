package com.obsidian.octopus.vulcan.filter;

import com.obsidian.octopus.utils.IpUtils;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;

/**
 *
 * @author alex
 */
@Singleton
public class IpFilter {

    private Map<Long, Long> iptables = new HashMap<>();

    public Map<Long, Long> getIptables() {
        return iptables;
    }

    public void setIptables(Map<Long, Long> iptables) {
        this.iptables = iptables;
    }

    public boolean isAllow(String ip) {
        long ipNumber = IpUtils.ip2Long(ip);
        boolean ret = false;
        for (Map.Entry<Long, Long> entrySet : iptables.entrySet()) {
            Long key = entrySet.getKey();
            Long value = entrySet.getValue();
            if (ipNumber >= key && ipNumber <= value) {
                ret = true;
                break;
            }
        }
        return ret;
    }

}
