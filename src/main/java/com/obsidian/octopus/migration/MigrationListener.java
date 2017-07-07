package com.obsidian.octopus.migration;

import com.obsidian.octopus.context.Context;
import com.obsidian.octopus.context.ContextProvider;
import com.obsidian.octopus.ioc.IocInstanceProvider;
import com.obsidian.octopus.listener.OctopusListener;
import javax.inject.Inject;

/**
 *
 * @author alex
 */
public class MigrationListener extends OctopusListener {

    @Inject
    private MigrationDatabase migrationDatabase;

    @Override
    public void onStart(Context context) throws Exception {
        IocInstanceProvider provider = ContextProvider.getInstance().getIocProvide();
        for (Class<? extends Migration> clazz : migrationDatabase.getMigrations()) {
            String name = clazz.getSimpleName();
            try {
                if (migrationDatabase.save(name)) {
                    Migration migration = (Migration) provider.getInstance(clazz);
                    migration.execute();
                }
            }
            catch (Exception e) {
                migrationDatabase.onException(name, e);
            }
        }
    }

}
