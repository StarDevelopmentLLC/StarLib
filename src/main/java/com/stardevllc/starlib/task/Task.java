package com.stardevllc.starlib.task;

public interface Task {
    int getTaskId();

    boolean isSync();

    boolean cancel();
}
