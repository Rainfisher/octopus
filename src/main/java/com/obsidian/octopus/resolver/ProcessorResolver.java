package com.obsidian.octopus.resolver;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex Chou
 */
public class ProcessorResolver {

    private Class key;
    private final List<Class> stacks;

    public ProcessorResolver() {
        this.stacks = new ArrayList<>();
    }

    public Class getKey() {
        return key;
    }

    public void setKey(Class key) {
        this.key = key;
    }

    public List<Class> getStacks() {
        return stacks;
    }

    public void addStack(Class stack) {
        this.stacks.add(stack);
    }

}
