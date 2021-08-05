package me.quexer.api.quexerapi;

import com.google.gson.Gson;
import lombok.Getter;
import me.quexer.api.quexerapi.event.EventManager;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class QuexerAPI {

    @Getter
    private Plugin plugin;
    @Getter
    private Gson gson;
    @Getter
    private EventManager eventManager;
    @Getter
    private ExecutorService executor;

    public QuexerAPI(Plugin plugin) {
        this.plugin = plugin;
        this.gson = new Gson();
        this.eventManager = new EventManager(this);
        this.executor = Executors.newCachedThreadPool();
    }

    public void removeMetadata(Entity entity, String metadata) {
        if(entity.hasMetadata(metadata)){
            entity.removeMetadata(metadata, plugin);
        }
    }
    public void setMetadata(Entity entity, String metadata, Object value) {
        removeMetadata(entity, metadata);
        entity.setMetadata(metadata, new FixedMetadataValue(plugin, value));
    }
    public void removeMetadata(Block block, String metadata) {
        if(block.hasMetadata(metadata)){
            block.removeMetadata(metadata, plugin);
        }
    }
    public void setMetadata(Block block, String metadata, Object value) {
        removeMetadata(block, metadata);
        block.setMetadata(metadata, new FixedMetadataValue(plugin, value));
    }

    public void registerEvent(Class<? extends Event> cls, EventManager.EventListener listener) {
        this.eventManager.registerEvent(cls, listener);
    }

    public void unregisterEvent(Class<? extends Event> cls, EventManager.EventListener listener) {
        this.eventManager.unregisterEvent(cls, listener);
    }

    public void registerCommand(String command, CommandExecutor executor) {
        this.eventManager.onCommand(command, executor);
    }

    public BukkitTask runLater(long delay, Runnable runnable) {
        return this.plugin.getServer().getScheduler().runTaskLater(plugin, runnable, delay);

    }
    public BukkitTask runTask(Runnable runnable) {
        return this.plugin.getServer().getScheduler().runTask(plugin, runnable);

    }


    public BukkitTask schedule(long delay, long period, Runnable runnable) {
        return this.plugin.getServer().getScheduler().runTaskTimer(plugin, runnable, delay, period);
    }

    public BukkitTask runTaskAsync(long delay, long period, Runnable runnable) {
        return this.plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, period);

    }

    public BukkitTask runTaskLaterAsync(long delay, Runnable runnable) {
        return this.plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);

    }


}
