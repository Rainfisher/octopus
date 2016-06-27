package com.obsidian.octopus.vulcan.core;

/**
 *
 * @author alex
 */
public interface Action {

    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";

    boolean execute() throws Exception;

}
