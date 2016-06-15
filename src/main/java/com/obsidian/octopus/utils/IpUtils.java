package com.obsidian.octopus.utils;

import com.obsidian.octopus.vulcan.object.ActionContext;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Map;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author Alex Chou
 */
public class IpUtils {

    public static String getLocalIp() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            return address.getHostAddress();
        }
        catch (UnknownHostException e) {
            return InetAddress.getLoopbackAddress().getHostAddress();
        }
    }

    public static String getIp(IoSession session) {
        InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
        return address == null ? "127.0.0.1" : address.getHostString();
    }

    public static long ip2Long(String ip) {
        try {
            String[] dots = ip.split("[.]");
            return (Integer.valueOf(dots[0]) << 24) + (Integer.valueOf(dots[1]) << 16)
                    + (Integer.valueOf(dots[2]) << 8) + Integer.valueOf(dots[3]);
        }
        catch (Exception e) {
            return 0L;
        }
    }

    public static HostPort getHostPort(String serverLabel) {
        String[] splits = serverLabel.split(":");
        return new HostPort(splits[0], Integer.valueOf(splits[1]));
    }

    public static String getServerLable(String host, int port) {
        return String.format("%s:%s", host, port);
    }

    /**
     * 得到真实的IP地址
     *
     * @return
     */
    public static String getIpAddr() {
        Map<String, String> headers = ActionContext.getRequestHeaders();
        String ip = headers.get("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.get("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.get("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            IoSession ioSession = (IoSession) ActionContext.get(ActionContext.IO_SESSION);
            InetSocketAddress address = (InetSocketAddress) ioSession.getRemoteAddress();
            ip = address == null ? "127.0.0.1" : address.getHostString();
        }
        return ip.split(",")[0];
    }

    public static class HostPort {

        private final String host;
        private final int port;

        public HostPort(String host, int port) {
            this.host = host;
            this.port = port;
        }

        public HostPort(String host) {
            String[] split = host.split(":");
            this.host = split[0];
            this.port = Integer.valueOf(split[1]);
        }

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }

        @Override
        public String toString() {
            return getServerLable(host, port);
        }
    }
}
