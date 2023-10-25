package com.stardevllc.starlib.task;

/**
 * A simple interface for information related to a Scheduler task
 */
public interface Task {
    /**
     * @return The auto-generated ID of the task
     */
    int getTaskId();

    /**
     * @return True if this task is syncronous or if it is asyncronous
     */
    boolean isSync();

    /**
     * Cancels the task. This only works for delayed and repeating tasks
     */
    boolean cancel();
}
