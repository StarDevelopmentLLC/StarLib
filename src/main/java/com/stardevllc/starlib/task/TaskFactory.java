package com.stardevllc.starlib.task;

/**
 * This class allows running tasks either now, with a delay or with a delay and a period<br>
 * This is mainly meant for use of abstracting code that is primarily used with Spigot as the structure is similar to that of the BukkitScheduler<br>
 * If you want a default implementation, the Java Timer class works pretty well, just implement this class and use the timer for the later methods
 */
public interface TaskFactory {
    /**
     * Runs a runnable task. This really only exists for compatibility with Spigot's scheduler
     * @param runnable The runnable to run
     * @return The Task information
     */
    Task runTask(Runnable runnable);

    /**
     * Runs a runnable task in an Async Thread.
     * @param runnable The runnable to run
     * @return The Task information
     */
    Task runTaskAsynchronously(Runnable runnable);

    /**
     * Runs a task with a given delay
     * @param runnable The runnable to run
     * @param delay The delay in milliseconds (for non Spigot uses) and ticks for use with Spigot
     * @return The task information
     */
    Task runTaskLater(Runnable runnable, long delay);

    /**
     * Runs a task async with a given delay
     * @param runnable The runnable to run
     * @param delay The delay in milliseconds (for non Spigot uses) and ticks for use with Spigot
     * @return The task information
     */
    Task runTaskLaterAsynchronously(Runnable runnable, long delay);

    /**
     * Runs a task on a timer and an initial delay
     * @param runnable The runnable task
     * @param delay The initial delay in milliseconds (for non Spigot uses) and ticks for use with Spigot
     * @param period The period in millseconds for non-Spigot uses and ticks for Spigot uses
     * @return The task information
     */
    Task runTaskTimer(Runnable runnable, long delay, long period);

    /**
     * Runs an async task on a timer and an initial delay
     * @param runnable The runnable task
     * @param delay The initial delay in milliseconds (for non Spigot uses) and ticks for use with Spigot
     * @param period The period in millseconds for non-Spigot uses and ticks for Spigot uses
     * @return The task information
     */
    Task runTaskTimerAsynchronously(Runnable runnable, long delay, long period);
}