package com.obsidian.octopus.vulcan.codec;

/**
 *
 * @author alex
 */
public class RequestMessage {

    private long receivedAt;
    private long executeAt;

    public long getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(long receivedAt) {
        this.receivedAt = receivedAt;
    }

    public long getExecuteAt() {
        return executeAt;
    }

    public void setExecuteAt(long executeAt) {
        this.executeAt = executeAt;
    }

}
