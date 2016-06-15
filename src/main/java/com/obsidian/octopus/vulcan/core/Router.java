package com.obsidian.octopus.vulcan.core;

/**
 *
 * @author alex
 */
public interface Router {

    Action getAction(String context);

}
