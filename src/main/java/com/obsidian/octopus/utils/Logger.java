package com.obsidian.octopus.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class Logger implements org.slf4j.Logger {

    public static boolean DEV = false;

    private final Set<String> messageSet;

    public static Logger getInstance(String name) {
        return LoggerHolder.getLogger(name);
    }

    public static Logger getInstance(Class clazz) {
        return LoggerHolder.getLogger(clazz);
    }

    private final org.slf4j.Logger logger;

    private Logger(org.slf4j.Logger logger) {
        this.logger = logger;
        this.messageSet = new HashSet<>();
    }

    @Override
    public String getName() {
        return logger.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public void trace(String string) {
        logger.trace(string);
    }

    @Override
    public void trace(String string, Object o) {
        logger.trace(string, o);
    }

    @Override
    public void trace(String string, Object o, Object o1) {
        logger.trace(string, o, o1);
    }

    @Override
    public void trace(String string, Object[] os) {
        logger.trace(string, os);
    }

    @Override
    public void trace(String string, Throwable thrwbl) {
        logger.trace(string, thrwbl);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return logger.isTraceEnabled(marker);
    }

    @Override
    public void trace(Marker marker, String string) {
        logger.trace(marker, string);
    }

    @Override
    public void trace(Marker marker, String string, Object o) {
        logger.trace(marker, string, o);
    }

    @Override
    public void trace(Marker marker, String string, Object o, Object o1) {
        logger.trace(marker, string, o, o1);
    }

    @Override
    public void trace(Marker marker, String string, Object[] os) {
        logger.trace(marker, string, os);
    }

    @Override
    public void trace(Marker marker, String string, Throwable thrwbl) {
        logger.trace(marker, string, thrwbl);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public void debug(String string) {
        logger.debug(string);
    }

    @Override
    public void debug(String string, Object o) {
        logger.debug(string, o);
    }

    @Override
    public void debug(String string, Object o, Object o1) {
        logger.debug(string, o, o1);
    }

    @Override
    public void debug(String string, Object[] os) {
        logger.debug(string, os);
    }

    @Override
    public void debug(String string, Throwable thrwbl) {
        logger.debug(string, thrwbl);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return logger.isDebugEnabled(marker);
    }

    @Override
    public void debug(Marker marker, String string) {
        logger.debug(marker, string);
    }

    @Override
    public void debug(Marker marker, String string, Object o) {
        logger.debug(marker, string, o);
    }

    @Override
    public void debug(Marker marker, String string, Object o, Object o1) {
        logger.debug(marker, string, o, o1);
    }

    @Override
    public void debug(Marker marker, String string, Object[] os) {
        logger.debug(marker, string, os);
    }

    @Override
    public void debug(Marker marker, String string, Throwable thrwbl) {
        logger.debug(marker, string, thrwbl);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public void info(String string) {
        logger.info(string);
    }

    @Override
    public void info(String string, Object o) {
        logger.info(string, o);
    }

    @Override
    public void info(String string, Object o, Object o1) {
        logger.info(string, o, o1);
    }

    @Override
    public void info(String string, Object[] os) {
        logger.info(string, os);
    }

    @Override
    public void info(String string, Throwable thrwbl) {
        logger.info(string, thrwbl);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return logger.isInfoEnabled(marker);
    }

    @Override
    public void info(Marker marker, String string) {
        logger.info(marker, string);
    }

    @Override
    public void info(Marker marker, String string, Object o) {
        logger.info(marker, string, o);
    }

    @Override
    public void info(Marker marker, String string, Object o, Object o1) {
        logger.info(marker, string, o, o1);
    }

    @Override
    public void info(Marker marker, String string, Object[] os) {
        logger.info(marker, string, os);
    }

    @Override
    public void info(Marker marker, String string, Throwable thrwbl) {
        logger.info(marker, string, thrwbl);
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public void warn(String string) {
        logger.warn(string);
    }

    @Override
    public void warn(String string, Object o) {
        logger.warn(string, o);
    }

    @Override
    public void warn(String string, Object[] os) {
        logger.warn(string, os);
    }

    @Override
    public void warn(String string, Object o, Object o1) {
        logger.warn(string, o, o1);
    }

    @Override
    public void warn(String string, Throwable thrwbl) {
        if (DEV) {
            logger.warn(string, thrwbl);
        } else {
            String exceptionString = _format(thrwbl);
            if (messageSet.add(exceptionString)) {
                logger.warn(_escape(string), exceptionString);
            }
        }
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return logger.isWarnEnabled(marker);
    }

    @Override
    public void warn(Marker marker, String string) {
        logger.warn(marker, string);
    }

    @Override
    public void warn(Marker marker, String string, Object o) {
        logger.warn(marker, string, o);
    }

    @Override
    public void warn(Marker marker, String string, Object o, Object o1) {
        logger.warn(marker, string, o, o1);
    }

    @Override
    public void warn(Marker marker, String string, Object[] os) {
        logger.warn(marker, string, os);
    }

    @Override
    public void warn(Marker marker, String string, Throwable thrwbl) {
        logger.warn(marker, string, thrwbl);
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public void error(String string) {
        logger.error(string);
    }

    @Override
    public void error(String string, Object o) {
        logger.error(string, o);
    }

    @Override
    public void error(String string, Object o, Object o1) {
        logger.error(string, o, o1);
    }

    @Override
    public void error(String string, Object[] os) {
        logger.error(string, os);
    }

    @Override
    public void error(String string, Throwable thrwbl) {
        if (DEV) {
            logger.error(string, thrwbl);
        } else {
            String exceptionString = _format(thrwbl);
            if (messageSet.add(exceptionString)) {
                logger.error(_escape(string), exceptionString);
            }
        }
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return logger.isErrorEnabled(marker);
    }

    @Override
    public void error(Marker marker, String string) {
        logger.error(marker, string);
    }

    @Override
    public void error(Marker marker, String string, Object o) {
        logger.error(marker, string, o);
    }

    @Override
    public void error(Marker marker, String string, Object o, Object o1) {
        logger.error(marker, string, o, o1);
    }

    @Override
    public void error(Marker marker, String string, Object[] os) {
        logger.error(marker, string, os);
    }

    @Override
    public void error(Marker marker, String string, Throwable thrwbl) {
        logger.error(marker, string, thrwbl);
    }

    private String _escape(String string) {
        if (!string.contains("{}")) {
            string += ": {}";
        }
        return string;
    }

    private String _format(Throwable e) {
        StringBuilder builder = new StringBuilder();
        if (e instanceof InvocationTargetException) {
            InvocationTargetException invocationTargetException = (InvocationTargetException) e;
            e = invocationTargetException.getTargetException();
        }
        builder.append(e.toString().replaceAll("\n", ";"))
                .append("||");
        builder.append(StringUtils.join(e.getStackTrace(), "||"));
        for (Throwable se : e.getSuppressed()) {
            builder.append("Suppressed:").append(StringUtils.join(se.getStackTrace(), "||"));
        }
        Throwable cause = e.getCause();
        if (cause != null) {
            builder.append("Cause:").append(StringUtils.join(cause.getStackTrace(), "||"));
        }
        return builder.toString();
    }

    private static class LoggerHolder {

        private static final Map<Object, Logger> MAP = new HashMap<>();

        private static Logger getLogger(String name) {
            return initLogger(name, LoggerFactory.getLogger(name));
        }

        private static Logger getLogger(Class clazz) {
            return initLogger(clazz, LoggerFactory.getLogger(clazz));
        }

        private static synchronized Logger initLogger(Object key, org.slf4j.Logger logger) {
            if (MAP.containsKey(key)) {
                return MAP.get(key);
            }
            Logger newLogger = new Logger(logger);
            MAP.put(key, newLogger);
            return newLogger;
        }
    }

}
