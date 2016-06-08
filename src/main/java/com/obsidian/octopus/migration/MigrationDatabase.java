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

    public void onStart() {
        for (Class<? extends Migration> clazz : this.getMigrations()) {
            String name = clazz.getSimpleName();
            try {
                if (!contains(name)) {
                    Migration migration = (Migration) injector.getInstance(clazz);
                    migration.up();
                    this.save(name);
                }
            }
            catch (Exception e) {
                LOGGER.warn("onStart: " + name, e);
            }
        }
    }

    public void onCombine() {
        for (Class<? extends Migration> clazz : this.getMigrations()) {
            String name = clazz.getSimpleName();
            try {
                if (!contains(name)) {
                    Migration migration = (Migration) injector.getInstance(clazz);
                    migration.combine();
                }
            }
            catch (Exception e) {
                LOGGER.warn("onCombine: " + name, e);
            }
        }
    }

    public abstract List<Class<? extends Migration>> getMigrations();

    public abstract boolean contains(String name);

    public abstract void save(String name);

}
