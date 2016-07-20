package com.obsidian.octopus.vulcan.object;

import com.obsidian.octopus.configuration.ConfigurationManager;
import com.obsidian.octopus.context.ContextProvider;
import com.obsidian.octopus.utils.Logger;
import com.obsidian.octopus.vulcan.core.Action;
import com.obsidian.octopus.vulcan.core.Router;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.apache.commons.lang.ClassUtils;

/**
 *
 * @author alex
 */
@Singleton
public class RouterImpl implements Router {

    private static final Logger LOGGER = Logger.getInstance(RouterImpl.class);

    private final Set<String> routers = new HashSet<>();
    private final Map<Pattern, Class<? extends Action>> mapping = new HashMap<>();
    private final Map<String, Class<? extends Action>> caches = new HashMap<>();

    @Override
    public Action getAction(String context) {
        Action action = null;
        Class<? extends Action> clazz;

        synchronized (caches) {
            clazz = caches.get(context);
        }
        if (clazz == null) {
            clazz = getClazz(context);
        }

        if (clazz != null) {
            action = ContextProvider.getInstance().getIocProvide().getInstance(clazz);
        }

        return action;
    }

    private Class<? extends Action> getClazz(String context) {
        for (Map.Entry<Pattern, Class<? extends Action>> entry : mapping.entrySet()) {
            Pattern pattern = entry.getKey();
            Class<? extends Action> value = entry.getValue();

            Matcher matcher = pattern.matcher(context);
            if (matcher.matches()) {
                String[] group = new String[matcher.groupCount()];
                for (int i = 0; i < group.length; i++) {
                    group[i] = matcher.group(i + 1);
                }
                ActionContext.set(ActionContext.CONTEXT_MATCHER, group);
                return value;
            }
        }
        return null;
    }

    @Inject
    public void inject() {
        this.initialize();
    }

    public void initialize() {
        Properties properties = (Properties) ConfigurationManager.getInstance().getConfiguration("router");
        for (String stringPropertyName : properties.stringPropertyNames()) {
            String className = properties.getProperty(stringPropertyName);
            try {
                Class<? extends Action> clazz = (Class<? extends Action>) ClassUtils.getClass(className);
                registerRouter(stringPropertyName, clazz);
            }
            catch (ClassNotFoundException e) {
                LOGGER.warn("router action not find: {}", className);
            }
            catch (Exception e) {
                LOGGER.warn("router exception", e);
            }
        }
    }

    public void registerRouter(String router, Class<? extends Action> clazz) {
        Pattern pattern = Pattern.compile(router);
        if (routers.add(router)) {
            mapping.put(pattern, clazz);
        } else {
            LOGGER.error("router is duplicate: {}", router);
        }
    }

}
