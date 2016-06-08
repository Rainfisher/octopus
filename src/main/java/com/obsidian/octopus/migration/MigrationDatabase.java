package com.obsidian.octopus.migration;

/**
 *
 * @author alex
 */
public interface MigrationDatabase {

    void onStart();

    void onCombine();

}
