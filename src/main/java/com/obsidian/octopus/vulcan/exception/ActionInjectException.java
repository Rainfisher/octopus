package com.obsidian.octopus.vulcan.exception;

/**
 *
 * @author alex
 */
public class ActionInjectException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of <code>ActionInjectException</code> without
     * detail message.
     */
    public ActionInjectException() {
    }

    /**
     * Constructs an instance of <code>ActionInjectException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ActionInjectException(String msg) {
        super(msg);
    }

}
