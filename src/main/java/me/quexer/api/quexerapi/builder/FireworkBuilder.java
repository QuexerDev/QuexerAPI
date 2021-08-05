package me.quexer.api.quexerapi.builder;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkBuilder {

    private Firework firework;
    private FireworkMeta fireworkMeta;

    public FireworkBuilder(Location location, FireworkEffect.Type type, Color color, int power) {
        this.firework = location.getWorld().spawn(location, Firework.class);
        FireworkEffect fireworkEffect = FireworkEffect.builder().withColor(color).flicker(true).trail(true).withFade(color).with(type).build();
        (this.fireworkMeta = this.firework.getFireworkMeta()).addEffect(fireworkEffect);
        this.fireworkMeta.setPower(power);
    }

    public FireworkBuilder build() {
        this.firework.setFireworkMeta(fireworkMeta);
        return null;
    }
}
