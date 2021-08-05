package me.quexer.api.quexerapi.misc;

import me.quexer.api.quexerapi.QuexerAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class Countdown {

    private BukkitTask task;
    private int high;
    private Runnable schedRunnable;
    private Runnable finishRunnable;
    private int start;
    private int timer;
    private boolean started = false;
    private Plugin plugin;


    public Countdown(Plugin plugin, int high, int start, int timer) {
        this.high = high;
        this.start = start;
        this.timer = timer;
        this.plugin = plugin;
    }

    public Countdown onSched(Runnable runnable) {
        schedRunnable = runnable;
        return this;
    }
    public Countdown onFinish(Runnable runnable) {
        finishRunnable = runnable;
        return this;
    }

    public Countdown start() {
        setStarted(true);
        task = Bukkit.getScheduler().runTaskTimerAsynchronously((plugin), () -> {
            if(high == 0) {
                finishRunnable.run();
                stop();
            } else {
                schedRunnable.run();
                high--;
            }
        }, start, timer*20);
        return this;
    }

    public void stop() {
        getTask().cancel();
    }

    public BukkitTask getTask() {
        return task;
    }

    public int getHigh() {
        return high;
    }

    public Runnable getSchedRunnable() {
        return schedRunnable;
    }

    public int getStart() {
        return start;
    }

    public int getTimer() {
        return timer;
    }

    public void setTask(BukkitTask task) {
        this.task = task;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public void setSchedRunnable(Runnable schedRunnable) {
        this.schedRunnable = schedRunnable;
    }

    public void setFinishRunnable(Runnable finishRunnable) {
        this.finishRunnable = finishRunnable;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public Runnable getFinishRunnable() {
        return finishRunnable;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
