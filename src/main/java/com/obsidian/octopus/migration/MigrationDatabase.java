package com.obsidian.octopus.migration;

import com.google.inject.Inject;
import com.google.inject.Injector;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author alex
 */
public abstract class MigrationDatabase {

    private static final Logger LOGGER = LoggerFactory.getLogger(MigrationDatabase.class);

    @Inject
    private Injector injector;

    public abstract List<Class<? extends Migration>> getMigrations();

    public abstract boolean save(String name);

    public void onStart() {
        for (Class<? extends Migration> clazz : this.getMigrations()) {
            String name = clazz.getSimpleName();
            try {
                if (save(name)) {
                    Migration migration = (Migration) injector.getInstance(clazz);
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
