package org.quartz.datamaster.scheduler;

/**
 * Created by magneto on 17-3-15.
 */
public interface Accepter<T extends Task> {
    void accept(T job);
}
