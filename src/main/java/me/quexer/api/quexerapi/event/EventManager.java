package me.quexer.api.quexerapi.event;

import me.quexer.api.quexerapi.QuexerAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author LeonEnkn
 *
 * Copyright (c) 2015 - 2018 by ShortByte.me to present. All rights reserved.
 */
public class EventManager implements Listener {

    private Plugin plugin;
    private final HashMap<EventListener, CopyOnWriteArrayList<ListenerExecutor>> executors = new HashMap();
    private final HashMap<String, CommandExecutor> commands = new HashMap();

    public EventManager(QuexerAPI plugin) {
        this.plugin = plugin.getPlugin();
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    public void registerEvent(Class<? extends Event> cls, EventListener listener) {
        ListenerExecutor executor = new ListenerExecutor(cls, listener);
        this.plugin.getServer().getPluginManager().registerEvent(cls, new Listener() { }, EventPriority.NORMAL, executor, this.plugin);
        if (!this.executors.containsKey(listener)) {
            this.executors.put(listener, new CopyOnWriteArrayList());
        }

        ((CopyOnWriteArrayList)this.executors.get(listener)).add(executor);
    }

    public void unregisterEvent(Class<? extends Event> cls, EventListener listener) {
        if (this.executors.containsKey(listener)) {
            executors.get(listener).stream().filter(executor -> executor.getListener().equals(listener)).forEach(executor -> {
                executor.setDisable(true);
            });
        }

    }

    public void onCommand(String command, CommandExecutor commandExecutor) {
        this.commands.put(command.toLowerCase(), commandExecutor);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        ArrayList<String> args = new ArrayList(Arrays.asList(event.getMessage().replaceFirst("/", "").split(" ")));
        if (this.commands.containsKey(((String)args.get(0)).toLowerCase())) {
            CommandExecutor executor = (CommandExecutor)this.commands.get(((String)args.get(0)).toLowerCase());
            args.remove(0);
            executor.onCommand(event.getPlayer(), (Command)null, (String)null, (String[])args.toArray(new String[0]));
            event.setCancelled(true);
        }
    }

    public HashMap<String, CommandExecutor> getCommands() {
        return commands;
    }


    public interface EventListener<T extends Event> {

        public void on(T event);
    }
}

