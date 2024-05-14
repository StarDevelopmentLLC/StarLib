package com.stardevllc.starlib.task;

public interface TaskFactory {
    Task runTask(Runnable runnable);

    Task runTaskAsynchronously(Runnable runnable);

    Task runTaskLater(Runnable runnable, long delay);

    Task runTaskLaterAsynchronously(Runnable runnable, long delay);

    Task runTaskTimer(Runnable runnable, long delay, long period);

    Task runTaskTimerAsynchronously(Runnable runnable, long delay, long period);
}