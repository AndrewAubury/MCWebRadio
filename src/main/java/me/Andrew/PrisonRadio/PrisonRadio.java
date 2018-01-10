package me.Andrew.PrisonRadio;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import me.Andrew.PrisonRadio.commands.controlsCommand;
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
                new listCommand(),
                new controlsCommand()
        };


        APIBuilder builder = new APIBuilder(this);
        builder.setCommandClasses(commands);

        getCommand("prisonradio").setExecutor(builder.getCommandHandler());
        Selector s = new Selector();
        s.setInvIfNull();
    }

    @Override
    public void onDisable() {
        //sb.stopServer();
    }


    public void setMusicScore(Player p, String song, String progress){
        String p1 = ChatColor.GREEN+"Playing: ";
        String p2 = ChatColor.DARK_GREEN+song;
        String p3 = ChatColor.GREEN+"Progress: ";
        String p4 = ChatColor.DARK_GREEN+progress;
        ActionBarAPI.sendActionBar(p,p1+p2+ChatColor.GOLD+"  |  "+p3+p4);

    }
    public void resetScoreBoard(Player p){
        ActionBarAPI.sendActionBar(p,"");
    }

}
