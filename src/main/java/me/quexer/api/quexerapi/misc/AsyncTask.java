package me.quexer.api.quexerapi.misc;

import me.quexer.api.quexerapi.QuexerAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class AsyncTask {


    public AsyncTask(Plugin plugin, Runnable run) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, run);
    }
}
