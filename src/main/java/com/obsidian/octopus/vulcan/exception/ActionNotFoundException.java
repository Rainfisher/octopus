package com.obsidian.octopus.vulcan.exception;

/**
 *
 * @author alex
 */
public class ActionNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of <code>ActionInjectException</code> without
     * detail message.
     */
    public ActionNotFoundException() {
    }

    /**
     * Constructs an instance of <code>ActionInjectException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ActionNotFoundException(String msg) {
        super(msg);
    }

}
