/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.quexer.api.quexerapi.builder.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 *
 * @author Elija
 */
public class GuiItem {
    
    private final Consumer<Player> consumer;

    private ItemStack itemStack;
    private InventoryGui inventory;

    public GuiItem(ItemStack itemStack, Consumer<Player> consumer) {
        this.itemStack = itemStack;
        this.consumer = consumer;
    }

    //<editor-fold defaultstate="collapsed" desc="compareItems">
    public boolean compareItems(ItemStack itemStack) {
        if (this.itemStack == null || itemStack == null || this.itemStack.getItemMeta() == null || itemStack.getItemMeta() == null
                || this.itemStack.getItemMeta().getDisplayName() == null || itemStack.getItemMeta().getDisplayName() == null)
            return false;
        if (this.itemStack.getItemMeta().getLore() != null || itemStack.getItemMeta().getLore() != null)
            if (!this.itemStack.getItemMeta().getLore().equals(itemStack.getItemMeta().getLore()))
                return false;
        if (this.itemStack.getAmount() != itemStack.getAmount())
            return false;
        return this.itemStack.getItemMeta().getDisplayName().equals(itemStack.getItemMeta().getDisplayName());
    }
    //</editor-fold>

    public boolean checkItem(ItemStack check) {
        if (this.itemStack == null || check == null)
            return false;
        if (this.itemStack.getItemMeta() == null || check.getItemMeta() == null)
            return false;
        if (this.itemStack.getItemMeta().getDisplayName() == null || check.getItemMeta().getDisplayName() == null)
            return false;
        if (this.itemStack.getItemMeta().getLore() != null && check.getItemMeta().getLore() != null &&
                !this.itemStack.getItemMeta().getLore().equals(check.getItemMeta().getLore()))
            return false;
        if (this.itemStack.getAmount() != check.getAmount())
            return false;
        return this.itemStack.getItemMeta().getDisplayName().equals(check.getItemMeta().getDisplayName());
    }

    //<editor-fold defaultstate="collapsed" desc="click">
    public void click(Player player) {
        if (consumer != null)
            consumer.accept(player);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getItemStack">
    public ItemStack getItemStack() {
        return itemStack;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setItemStack">
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setInventory">
    public void setInventory(InventoryGui inventory) {
        this.inventory = inventory;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update">
    public void update() {
        inventory.update(this);
    }
    //</editor-fold>

    
    
}
