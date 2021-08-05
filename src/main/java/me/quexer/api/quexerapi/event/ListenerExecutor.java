package me.quexer.api.quexerapi.event;

import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

/**
 *
 * @author LeonEnkn
 *
 * Copyright (c) 2015 - 2018 by ShortByte.me to present. All rights reserved.
 */
public class ListenerExecutor implements EventExecutor {

    private final Class<? extends Event> cls;
    private final EventManager.EventListener listener;

    private boolean disable = false;

    public ListenerExecutor(Class<? extends Event> cls, EventManager.EventListener listener) {
        this.cls = cls;
        this.listener = listener;
    }

    //<editor-fold defaultstate="collapsed" desc="execute">
    @Override
    public void execute(Listener ll, Event event) throws EventException {
        if (this.disable) {
            event.getHandlers().unregister(ll);
            return;
        }
        if (this.cls.equals(event.getClass()))
            this.listener.on(event);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getListener">
    public EventManager.EventListener getListener() {
        return listener;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setDisable">
    public void setDisable(boolean disable) {
        this.disable = disable;
    }
    //</editor-fold>

}

