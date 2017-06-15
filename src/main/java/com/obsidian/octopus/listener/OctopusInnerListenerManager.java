package com.obsidian.octopus.listener;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alex
 */
public class OctopusInnerListenerManager {

    public static final String LISTENER_CONFIG = "config";
    public static final String LISTENER_IOC = "ioc";
    public static final String LISTENER_CONFIG_CALLBACK = "config_callback";
    public static final String LISTENER_MODULE_LISTENER = "listener";
    public static final String LISTENER_QUARTZ = "quartz";
    public static final String LISTENER_START = "start";

    private static final List<Entry> LISTENERS = new ArrayList<>();

    static {
        addLast(LISTENER_CONFIG, new OctopusInnerListenerConfig());
        addLast(LISTENER_IOC, new OctopusInnerListenerIoc());
        addLast(LISTENER_CONFIG_CALLBACK, new OctopusInnerListenerConfigCallback());
        addLast(LISTENER_MODULE_LISTENER, new OctopusInnerListenerModuleListener());
        addLast(LISTENER_QUARTZ, new OctopusInnerListenerQuartz());
        addLast(LISTENER_START, new OctopusInnerListenerStart());
    }

    public static List<OctopusInnerListener> getListeners() {
        List<OctopusInnerListener> list = new ArrayList<>();
        for (Entry entry : LISTENERS) {
            list.add(entry.listener);
        }
        return list;
    }

    public static void addLast(String name, OctopusInnerListener listener) {
        _checkUniqueName(name);
        LISTENERS.add(new Entry(name, listener));
    }

    public static void addAfter(String pointName, String name, OctopusInnerListener listener) {
        _checkUniqueName(name);
        int i = _indexOf(pointName);
        if (i == -1) {
            throw new IllegalArgumentException("Point listener not exist: " + pointName);
        }
        LISTENERS.add(i + 1, new Entry(name, listener));
    }

    private static void _checkUniqueName(String name) {
        int index = _indexOf(name);
        if (index != -1) {
            throw new IllegalArgumentException("Other listener is using the same name: " + name);
        }
    }

    private static int _indexOf(String name) {
        for (int i = 0; i < LISTENERS.size(); i++) {
            Entry e = LISTENERS.get(i);
            if (name.equals(e.name)) {
                return i;
            }
        }
        return -1;
    }

    private static class Entry {

        private final String name;
        private final OctopusInnerListener listener;

        public Entry(String name, OctopusInnerListener listener) {
            this.name = name;
            this.listener = listener;
        }

        public String getName() {
            return name;
        }

        public OctopusInnerListener getListener() {
            return listener;
        }

    }

}
