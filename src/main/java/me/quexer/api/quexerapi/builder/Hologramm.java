package me.quexer.api.quexerapi.builder;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Hologramm {

    private List<String> lines;
    private Location loc;
    private static final double ABS = 0.23D;
    private static String path = Bukkit.getServer().getClass().getPackage().getName();
    private static String version = path.substring(path.lastIndexOf(".") + 1, path.length());

    public Hologramm(Location loc, List<String> lines)
    {
        this.lines = lines;
        this.loc = loc;
    }

    public boolean display(Player p)
    {
        Location displayLoc = this.loc.clone().add(0.0D, 0.23D * this.lines.size() - 1.97D, 0.0D);
        for (int i = 0; i < this.lines.size(); i++)
        {
            Object packet = getPacket(this.loc.getWorld(), displayLoc.getX(), displayLoc.getY(), displayLoc.getZ(), (String)this.lines.get(i));
            if (packet == null) {
                return false;
            }
            sendPacket(p, packet);
            displayLoc.add(0.0D, -0.23D, 0.0D);
        }
        return true;
    }

    public Object getPacket(World w, double x, double y, double z, String text)
    {
        try
        {
            Class<?> armorStand = Class.forName("net.minecraft.server." + version + ".EntityArmorStand");
            Class<?> worldClass = Class.forName("net.minecraft.server." + version + ".World");
            Class<?> nmsEntity = Class.forName("net.minecraft.server." + version + ".Entity");
            Class<?> craftWorld = Class.forName("org.bukkit.craftbukkit." + version + ".CraftWorld");
            Class<?> packetClass = Class.forName("net.minecraft.server." + version + ".PacketPlayOutSpawnEntityLiving");
            Class<?> entityLivingClass = Class.forName("net.minecraft.server." + version + ".EntityLiving");
            Constructor<?> cww = armorStand.getConstructor(new Class[] { worldClass });
            Object craftWorldObj = craftWorld.cast(w);
            Method getHandleMethod = craftWorldObj.getClass().getMethod("getHandle", new Class[0]);
            Object entityObject = cww.newInstance(new Object[] { getHandleMethod.invoke(craftWorldObj, new Object[0]) });
            Method setCustomName = entityObject.getClass().getMethod("setCustomName", new Class[] { String.class });
            setCustomName.invoke(entityObject, new Object[] { text });
            Method setCustomNameVisible = nmsEntity.getMethod("setCustomNameVisible", new Class[] { Boolean.TYPE });
            setCustomNameVisible.invoke(entityObject, new Object[] { Boolean.valueOf(true) });
            Method setGravity = entityObject.getClass().getMethod("setGravity", new Class[] { Boolean.TYPE });
            setGravity.invoke(entityObject, new Object[] { Boolean.valueOf(false) });
            Method setLocation = entityObject.getClass().getMethod("setLocation", new Class[] { Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE });
            setLocation.invoke(entityObject, new Object[] { Double.valueOf(x), Double.valueOf(y), Double.valueOf(z), Float.valueOf(0.0F), Float.valueOf(0.0F) });
            Method setInvisible = entityObject.getClass().getMethod("setInvisible", new Class[] { Boolean.TYPE });
            setInvisible.invoke(entityObject, new Object[] { Boolean.valueOf(true) });
            Constructor<?> cw = packetClass.getConstructor(new Class[] { entityLivingClass });
            return cw.newInstance(new Object[] { entityObject });
        }
        catch (ClassNotFoundException|NoSuchMethodException|SecurityException|InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private void sendPacket(Player p, Object packet)
    {
        String path = Bukkit.getServer().getClass().getPackage().getName();
        String version = path.substring(path.lastIndexOf(".") + 1, path.length());
        try
        {
            Method getHandle = p.getClass().getMethod("getHandle", new Class[0]);
            Object entityPlayer = getHandle.invoke(p, new Object[0]);
            Object pConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            Class<?> packetClass = Class.forName("net.minecraft.server." + version + ".Packet");
            Method sendMethod = pConnection.getClass().getMethod("sendPacket", new Class[] { packetClass });
            sendMethod.invoke(pConnection, new Object[] { packet });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
