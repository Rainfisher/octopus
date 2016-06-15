package com.obsidian.octopus.vulcan.filter;

import com.obsidian.octopus.vulcan.annotation.ResponseFilterBy;
import com.obsidian.octopus.vulcan.core.Action;
import javax.inject.Singleton;

/**
 *
 * @author alex
 */
@Singleton
public class ResponseFilterManager {

    private Class<? extends ResponseFilter> filter = ResponseDefaultFilter.class;

    public void setDefaultFilter(Class<? extends ResponseFilter> filter) {
        this.filter = filter;
    }

    public ResponseFilter getFilter(Action action) {
        Class<? extends Action> clazz = action.getClass();
        ResponseFilterBy filterBy = clazz.getAnnotation(ResponseFilterBy.class);
        return filterBy == null ? _getInstance(filter) : _getInstance(filterBy.value());
    }

    private ResponseFilter _getInstance(Class<? extends ResponseFilter> filterClass) {
        ResponseFilter filter = null;
        try {
            filter = filterClass.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return filter;
    }

}
