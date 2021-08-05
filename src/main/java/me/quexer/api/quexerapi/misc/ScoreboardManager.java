package me.quexer.api.quexerapi.misc;

public class ScoreboardManager {

    /*private org.bukkit.scoreboard.Scoreboard board;
    private Objective obj;
    private net.minecraft.server.v1_8_R3.Scoreboard sb;
    private HashMap<Group, Team> groupTeamHashMap = new HashMap<>();


    public ScoreboardManager() {
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        obj = board.registerNewObjective("aaaaa", "bbbbb");

        for (int i = 0; i < Group.values().length; i++) {
            Group manager = Group.values()[i];
            Team team = board.registerNewTeam("" + manager.getTabID() + manager.toString());
            System.out.println("" + manager.getTabID() + manager.toString());
            team.setPrefix(manager.getPrefix());
            groupTeamHashMap.put(manager, team);
        }
    }

    public void setTablist(Player player) {
        BackendPlayer user = (BackendPlayer) player.getMetadata("user").get(0).value();
        groupTeamHashMap.get(user.getGroup()).addPlayer(player);

        player.setPlayerListName(user.getGroup().getPrefix() + player.getName());
        player.setDisplayName(user.getGroup().getPrefix() + player.getName());

        Bukkit.getOnlinePlayers().forEach(o -> {
            o.setScoreboard(board);
        });
    }

    public void setBoard(Player player) {
        sb = new net.minecraft.server.v1_8_R3.Scoreboard();

        ScoreboardObjective obj = sb.registerObjective("§8» §6FlashLabs", IScoreboardCriteria.b);
        PacketPlayOutScoreboardObjective createpacket = new PacketPlayOutScoreboardObjective(obj, 0);
        PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, obj);
        String status = "";
        BackendPlayer user = (BackendPlayer) player.getMetadata("user").get(0).value();
        if (user.getReady() == 1)
            status = " §8» §aJa";
        else if(user.getReady() == 0)
            status = " §8» §cNein";
        else
            status = " §8» §aAngenommen";
        ScoreboardScore a = new ScoreboardScore(sb, obj, "§a§8§m-----------------");
        ScoreboardScore a1 = new ScoreboardScore(sb, obj, "§7Rang:");
        ScoreboardScore a2 = new ScoreboardScore(sb, obj, " §8» " + user.getGroup().getName());
        ScoreboardScore a3 = new ScoreboardScore(sb, obj, "§d");
        ScoreboardScore a4 = new ScoreboardScore(sb, obj, "§7Status:");
        ScoreboardScore a5 = new ScoreboardScore(sb, obj, status);
        ScoreboardScore a6 = new ScoreboardScore(sb, obj, "§0");
        ScoreboardScore a7 = new ScoreboardScore(sb, obj, "§7Twitter:");
        ScoreboardScore a8 = new ScoreboardScore(sb, obj, " §8» §b@FlashlabsDe");
        ScoreboardScore a10 = new ScoreboardScore(sb, obj, "§4");
        ScoreboardScore a11 = new ScoreboardScore(sb, obj, "§7Website:");
        ScoreboardScore a12 = new ScoreboardScore(sb, obj, " §8» §6SplashLabs.de");
        ScoreboardScore a9 = new ScoreboardScore(sb, obj, "§7§8§m-----------------");

        a.setScore(12);
        a1.setScore(11);
        a2.setScore(10);
        a3.setScore(9);
        a4.setScore(8);
        a5.setScore(7);
        a6.setScore(6);
        a7.setScore(5);
        a8.setScore(4);
        a10.setScore(3);
        a11.setScore(2);
        a12.setScore(1);
        a9.setScore(0);



        PacketPlayOutScoreboardObjective removePacket = new PacketPlayOutScoreboardObjective(obj, 1);
        PacketPlayOutScoreboardScore pa = new PacketPlayOutScoreboardScore(a);
        PacketPlayOutScoreboardScore pa1 = new PacketPlayOutScoreboardScore(a1);
        PacketPlayOutScoreboardScore pa2 = new PacketPlayOutScoreboardScore(a2);
        PacketPlayOutScoreboardScore pa3 = new PacketPlayOutScoreboardScore(a3);
        PacketPlayOutScoreboardScore pa4 = new PacketPlayOutScoreboardScore(a4);
        PacketPlayOutScoreboardScore pa5 = new PacketPlayOutScoreboardScore(a5);
        PacketPlayOutScoreboardScore pa6 = new PacketPlayOutScoreboardScore(a6);
        PacketPlayOutScoreboardScore pa7 = new PacketPlayOutScoreboardScore(a7);
        PacketPlayOutScoreboardScore pa8 = new PacketPlayOutScoreboardScore(a8);
        PacketPlayOutScoreboardScore pa9 = new PacketPlayOutScoreboardScore(a9);
        PacketPlayOutScoreboardScore pa10 = new PacketPlayOutScoreboardScore(a10);
        PacketPlayOutScoreboardScore pa11 = new PacketPlayOutScoreboardScore(a11);
        PacketPlayOutScoreboardScore pa12 = new PacketPlayOutScoreboardScore(a12);
        sendPacket(player, removePacket);
        sendPacket(player, createpacket);
        sendPacket(player, display);
        sendPacket(player, pa);
        sendPacket(player, pa1);
        sendPacket(player, pa2);
        sendPacket(player, pa3);
        sendPacket(player, pa4);
        sendPacket(player, pa5);
        sendPacket(player, pa6);
        sendPacket(player, pa7);
        sendPacket(player, pa8);
        sendPacket(player, pa9);
        sendPacket(player, pa10);
        sendPacket(player, pa11);
        sendPacket(player, pa12);

        ServerAPI.getQuexerAPI().setMetadata(player, "scoreboard",sb);
    }

    public  void sendPacket(Player p, Packet packet) {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
    }

    public  void updateScoreboard(Player p) {
        Scoreboard sb = (Scoreboard) p.getMetadata("scoreboard").get(0).value();


        ScoreboardObjective obj = sb.getObjective("§8» §6FlashLabs");
        PacketPlayOutScoreboardObjective createpacket = new PacketPlayOutScoreboardObjective(obj, 0);
        PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, obj);

        String status = "";
        BackendPlayer user = (BackendPlayer) p.getMetadata("user").get(0).value();
        if (user.getReady() == 1)
            status = " §8» §aJa";
        else if(user.getReady() == 0)
            status = " §8» §cNein";
        else
            status = " §8» §aAngenommen";

        ScoreboardScore a = new ScoreboardScore(sb, obj, "§a§8§m-----------------");
        ScoreboardScore a1 = new ScoreboardScore(sb, obj, "§7Rang:");
        ScoreboardScore a2 = new ScoreboardScore(sb, obj, " §8» " + user.getGroup().getFull());
        ScoreboardScore a3 = new ScoreboardScore(sb, obj, "§d");
        ScoreboardScore a4 = new ScoreboardScore(sb, obj, "§7Status:");
        ScoreboardScore a5 = new ScoreboardScore(sb, obj, status);
        ScoreboardScore a6 = new ScoreboardScore(sb, obj, "§0");
        ScoreboardScore a7 = new ScoreboardScore(sb, obj, "§7Twitter:");
        ScoreboardScore a8 = new ScoreboardScore(sb, obj, " §8» §b@FlashlabsDe");
        ScoreboardScore a10 = new ScoreboardScore(sb, obj, "§4");
        ScoreboardScore a11 = new ScoreboardScore(sb, obj, "§7Website:");
        ScoreboardScore a12 = new ScoreboardScore(sb, obj, " §8» §6SplashLabs.de");
        ScoreboardScore a9 = new ScoreboardScore(sb, obj, "§7§8§m-----------------");

        a.setScore(12);
        a1.setScore(11);
        a2.setScore(10);
        a3.setScore(9);
        a4.setScore(8);
        a5.setScore(7);
        a6.setScore(6);
        a7.setScore(5);
        a8.setScore(4);
        a10.setScore(3);
        a11.setScore(2);
        a12.setScore(1);
        a9.setScore(0);



        PacketPlayOutScoreboardObjective removePacket = new PacketPlayOutScoreboardObjective(obj, 1);
        PacketPlayOutScoreboardScore pa = new PacketPlayOutScoreboardScore(a);
        PacketPlayOutScoreboardScore pa1 = new PacketPlayOutScoreboardScore(a1);
        PacketPlayOutScoreboardScore pa2 = new PacketPlayOutScoreboardScore(a2);
        PacketPlayOutScoreboardScore pa3 = new PacketPlayOutScoreboardScore(a3);
        PacketPlayOutScoreboardScore pa4 = new PacketPlayOutScoreboardScore(a4);
        PacketPlayOutScoreboardScore pa5 = new PacketPlayOutScoreboardScore(a5);
        PacketPlayOutScoreboardScore pa6 = new PacketPlayOutScoreboardScore(a6);
        PacketPlayOutScoreboardScore pa7 = new PacketPlayOutScoreboardScore(a7);
        PacketPlayOutScoreboardScore pa8 = new PacketPlayOutScoreboardScore(a8);
        PacketPlayOutScoreboardScore pa9 = new PacketPlayOutScoreboardScore(a9);
        PacketPlayOutScoreboardScore pa10 = new PacketPlayOutScoreboardScore(a10);
        PacketPlayOutScoreboardScore pa11 = new PacketPlayOutScoreboardScore(a11);
        PacketPlayOutScoreboardScore pa12 = new PacketPlayOutScoreboardScore(a12);
        sendPacket(p, removePacket);
        sendPacket(p, createpacket);
        sendPacket(p, display);
        sendPacket(p, pa);
        sendPacket(p, pa1);
        sendPacket(p, pa2);
        sendPacket(p, pa3);
        sendPacket(p, pa4);
        sendPacket(p, pa5);
        sendPacket(p, pa6);
        sendPacket(p, pa7);
        sendPacket(p, pa8);
        sendPacket(p, pa9);
        sendPacket(p, pa10);
        sendPacket(p, pa11);
        sendPacket(p, pa12);

        ServerAPI.getQuexerAPI().setMetadata(p, "scoreboard",sb);
    }
    */
}
