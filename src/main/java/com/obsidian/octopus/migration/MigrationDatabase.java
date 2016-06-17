package com.obsidian.octopus.migration;

import com.obsidian.octopus.configuration.ConfigurationManager;
import com.obsidian.octopus.context.ContextProvider;
import com.obsidian.octopus.ioc.IocInstanceProvider;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author alex
 */
public abstract class MigrationDatabase {

    private static final Logger LOGGER = LoggerFactory.getLogger(MigrationDatabase.class);

    private List<Class<? extends Migration>> _getMigrations() {
        List<Class<? extends Migration>> list = new ArrayList<>();
        String migrations = ConfigurationManager.getInstance().getConfiguration("migration");
        for (String string : migrations.split("\n")) {
            if (string == null || string.isEmpty()) {
                continue;
            }
            try {
                list.add((Class<? extends Migration>) ClassUtils.getClass(string));
            }
            catch (Exception e) {
                LOGGER.warn("getMigrations: " + string, e);
            }
        }
        return list;
    }

    public abstract boolean save(String name);

    public void onStart() {
        IocInstanceProvider provider = ContextProvider.getInstance().getIocProvide();
        for (Class<? extends Migration> clazz : this._getMigrations()) {
            String name = clazz.getSimpleName();
            try {
                if (save(name)) {
                    Migration migration = (Migration) provider.getInstance(clazz);
                    migration.execute();
                }
            }
            catch (Exception e) {
                this.onException(name, e);
            }
        }
    }

    public void onException(String name, Exception e) {
        LOGGER.warn("onStart: " + name, e);
    }

}
