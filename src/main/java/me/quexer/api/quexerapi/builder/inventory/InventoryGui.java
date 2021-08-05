/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.quexer.api.quexerapi.builder.inventory;

import me.quexer.api.quexerapi.QuexerAPI;
import me.quexer.api.quexerapi.builder.ItemBuilder;
import me.quexer.api.quexerapi.event.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elija
 */
public class InventoryGui {
    
    private final QuexerAPI plugin;

    private Inventory inventory;
    private final GuiItem[] items;
    private final String name;
    private boolean destroy;
    private boolean destroyOnClose;
    private final Consumer<Player> closeEvent;
    private int timedOpen = 0;

    private BukkitTask timedTask;
    private EventManager.EventListener<InventoryClickEvent> listenerClick;
    private EventManager.EventListener<InventoryCloseEvent> listenerClose;

    protected InventoryGui(QuexerAPI plugin, GuiItem[] items, String name, Inventory inventory, Consumer<Player> closeEvent) {
        this.plugin = plugin;
        this.name = name;
        this.items = items;
        this.closeEvent = closeEvent;

        createInventory(inventory);
        initListeners();
    }

    public void addItem(int i, GuiItem item) {
        try {
            this.items[i] = item;
            this.inventory.setItem(i, item.getItemStack().clone());
            item.setInventory(this);
        } catch (ArrayIndexOutOfBoundsException e) {
            Logger.getLogger(InventoryGui.class.getName()).log(Level.SEVERE, (String)null, e);
            Logger.getLogger(InventoryGui.class.getName()).log(Level.INFO, "Items {0}  {1}", new Object[] { Integer.valueOf(this.items.length), Integer.valueOf(i) });
        }
    }

    private void createInventory(Inventory inventory) {
        if (inventory == null) {
            this.inventory = this.plugin.getPlugin().getServer().createInventory(null, this.items.length, this.name);
        } else {
            this.inventory = inventory;
        }
        for (int i = 0; i < this.items.length; i++) {
            GuiItem item = this.items[i];
            if (item != null) {

                this.inventory.setItem(i, item.getItemStack().clone());
                item.setInventory(this);
            }
        }
    }

    public InventoryGui open(Player player) {
        fix();
        player.openInventory(this.inventory);
        return this;
    }

    public InventoryGui timedOpen(Player player) {
        this.inventory.clear();

        List<Integer> items = new ArrayList<>();

        for (int i = 0; i != this.items.length; i++) {
            GuiItem item = this.items[i];
            if (item != null && item.getItemStack() != null && !item.getItemStack().getType().equals(Material.STAINED_GLASS_PANE)) {
                items.add(Integer.valueOf(i));
            } else {
                this.inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte)8).setName(" ").toItemStack());
            }
        }
        player.openInventory(this.inventory);

        this.timedTask = this.plugin.schedule(2L, 2L, () -> {
            GuiItem item = this.items[((Integer)items.get(this.timedOpen)).intValue()];
            if (item == null)
                return;
            if (item.getItemStack() == null)
                return;
            player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
            this.inventory.setItem(((Integer)items.get(this.timedOpen)).intValue(), item.getItemStack().clone());
            item.setInventory(this);
            this.timedOpen++;
            if (this.timedOpen == items.size())
                this.timedTask.cancel();
        });
        return this;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public InventoryGui update(GuiItem gi) {
        for (int i = 0; i < this.items.length; i++) {
            GuiItem item = this.items[i];
            if (gi.equals(item))
            {
                this.inventory.setItem(i, item.getItemStack().clone()); }
        }
        return this;
    }

    public InventoryGui destroy() {
        this.destroy = true;
        this.plugin.getEventManager().unregisterEvent(InventoryClickEvent.class, this.listenerClick);
        this.plugin.getEventManager().unregisterEvent(InventoryCloseEvent.class, this.listenerClose);
        return this;
    }

    public boolean isDestroy() {
        return this.destroy;
    }

    public InventoryGui destroyOnClose() {
        this.destroyOnClose = true;
        return this;
    }

    private void initListeners() {
        this.listenerClick = (event -> {
            if (event.getClick().isShiftClick() && event.getInventory().equals(this.inventory)) {
                event.setCancelled(true);

                return;
            }
            if (!event.getWhoClicked().getOpenInventory().getTopInventory().equals(this.inventory)) {
                return;
            }
            if (event.getClickedInventory() == null || event.getWhoClicked() == null) {
                return;
            }
            if (event.getClickedInventory().equals(event.getWhoClicked().getInventory())) {
                return;
            }
            event.setCancelled(true);
            for (GuiItem item : this.items) {
                if (item != null) {
                    if (item.checkItem(event.getCurrentItem())) {
                        try {
                            item.click((Player)event.getWhoClicked());
                            break;
                        } catch (Exception e) {
                            Logger.getLogger(InventoryGui.class.getName()).log(Level.SEVERE, (String)null, e);
                        }
                    }
                }
            }
        });
        this.listenerClose = (event) -> {
            if (!this.destroy) {
                if (event.getInventory().equals(this.inventory)) {
                    this.plugin.getPlugin().getServer().getScheduler().runTaskAsynchronously(this.plugin.getPlugin(), () -> {
                        try {
                            if (this.closeEvent != null) {
                                this.closeEvent.accept((Player)event.getPlayer());
                            }
                        } catch (Exception var3) {
                            Logger.getLogger(InventoryGui.class.getName()).log(Level.SEVERE, (String)null, var3);
                        }

                    });
                    if (this.destroyOnClose) {
                        this.destroy();
                    }
                }
            }
        };

        this.plugin.getEventManager().registerEvent(InventoryClickEvent.class, this.listenerClick);
        this.plugin.getEventManager().registerEvent(InventoryCloseEvent.class, this.listenerClose);
    }

    public boolean isDestroyed() {
        return this.destroy;
    }

    public InventoryGui setDestroyOnClose() {
        this.destroyOnClose = true;
        return this;
    }

    public boolean isDestroyOnClose() {
        return this.destroyOnClose;
    }

    public void addItem(GuiItem item) {
        for (int i = 0; i < this.items.length; ) {
            if (this.items[i] != null) {
                i++; continue;
            }  addItem(i, item);
            return;
        }
    }

    public void removeItem(GuiItem item) {
        for (int i = 0; i < this.items.length; i++) {
            if (this.items[i] == item) {

                this.items[i] = null;
                this.inventory.remove(item.getItemStack());
                item.setInventory(null);
            }
        }
    }

    public InventoryGui update() {
        for (int i = 0; i < this.items.length; i++) {
            GuiItem item = this.items[i];
            if (item != null)
            {
                if (item.getItemStack() != null)
                {
                    this.inventory.setItem(i, item.getItemStack().clone()); }  }
        }
        return this;
    }

    public void fix() {
        for (int i = 0; i < this.items.length; i++) {
            GuiItem item = this.items[i];
            if (item != null)
            {
                if (item.getItemStack() != null) {

                    this.inventory.setItem(i, item.getItemStack().clone());
                    item.setInventory(this);
                }
            }
        }
    }

    public GuiItem getItem(int i) {
        return this.items[i];
    }

    public void clear() {
        this.inventory.clear();
        for (int i = 0; i < this.items.length; i++) {
            this.items[i] = null;
        }
    }

    public InventoryGui updateItem(GuiItem item, GuiItem updateItem) {
        for (int i = 0; i < this.items.length; i++) {
            GuiItem gi = this.items[i];
            if (item.equals(gi)) {

                this.items[i] = updateItem;
                this.inventory.setItem(i, updateItem.getItemStack().clone());
            }
        }  return this;
    }

}
