package com.obsidian.octopus.vulcan.object;

/**
 *
 * @author alex
 */
public enum SystemResponseCode implements ResponseCode {

    SUCCESS(0),
    UNKNOWN_ERROR(1),
    UNKNOWN_API(2),
    INVALID_PARAMETER(3);

    private final int id;

    private SystemResponseCode(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

}
