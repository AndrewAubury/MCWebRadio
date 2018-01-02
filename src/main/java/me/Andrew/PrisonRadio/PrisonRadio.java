package me.Andrew.PrisonRadio;

import me.Andrew.PrisonRadio.commands.listCommand;
import me.Andrew.PrisonRadio.commands.playCommand;
import me.Andrew.PrisonRadio.commands.playYoutubeCommand;
import me.Andrew.PrisonRadio.events.InvClickEvent;
import me.Andrew.PrisonRadio.events.JoinEvent;
import me.Andrew.PrisonRadio.events.LeaveEvent;
import me.Andrew.PrisonRadio.gui.Selector;
import me.Andrew.PrisonRadio.socketio.socketbot;
import me.piggypiglet.pigapi.APIBuilder;
import me.piggypiglet.pigapi.objects.CMD;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Created by Andrew on 24/12/2017.
 */
public class PrisonRadio extends JavaPlugin {
    private static PrisonRadio instance;
    public static PrisonRadio getInstance(){return instance;}

    public socketbot sb;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        sb = new socketbot(getConfig().getString("websocket.hostname"),getConfig().getInt("websocket.port"));
        sb.startServer();


        getServer().getPluginManager().registerEvents(new JoinEvent(),this);
        getServer().getPluginManager().registerEvents(new LeaveEvent(),this);
        getServer().getPluginManager().registerEvents(new InvClickEvent(),this);

        /* To add more classes, put a comma after playCommand(), and get the other class. Eg,
        new playCommand(),
        new oofCommand() */
        CMD[] commands = new CMD[] {
                new playCommand(),
                new playYoutubeCommand(),
                new listCommand()
        };

        new Selector();
        APIBuilder builder = new APIBuilder(this);
        builder.setCommandClasses(commands);

        getCommand("prisonradio").setExecutor(builder.getCommandHandler());

    }

    @Override
    public void onDisable() {
        //sb.stopServer();
    }


    public Scoreboard setMusicScore(Player p, String song, String progress){
        Scoreboard b = getServer().getScoreboardManager().getNewScoreboard();
        Objective o = b.registerNewObjective("Music","dummy");
        o.setDisplayName(ChatColor.GREEN+"Music");
        Score sNameTag = o.getScore(ChatColor.GREEN+"Playing: ");
        Score sName = o.getScore(ChatColor.DARK_GREEN+song);

        Score sPlaceholder = o.getScore(ChatColor.DARK_GREEN+"  ");

        Score sProgressTag = o.getScore(ChatColor.GREEN+"Progress: ");
        Score sProgress = o.getScore(ChatColor.DARK_GREEN+progress);

        sNameTag.setScore(10);
        sName.setScore(9);
        sPlaceholder.setScore(8);
        sProgressTag.setScore(7);
        sProgress.setScore(6);

        p.setScoreboard(b);
        return b;
    }
    public void resetScoreBoard(Player p){
        p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
    }

}
