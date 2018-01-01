package me.Andrew.PrisonRadio;

import com.corundumstudio.socketio.SocketIOClient;
import me.Andrew.PrisonRadio.commands.playCommand;
import me.Andrew.PrisonRadio.events.JoinEvent;
import me.Andrew.PrisonRadio.events.LeaveEvent;
import me.Andrew.PrisonRadio.socketio.socketbot;
import me.piggypiglet.pigapi.APIBuilder;
import me.piggypiglet.pigapi.handlers.CommandHandler;
import me.piggypiglet.pigapi.objects.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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

        /* To add more classes, put a comma after playCommand(), and get the other class. Eg,
        new playCommand(),
        new oofCommand() */
        Command[] commands = new Command[] {
                new playCommand()
        };
        APIBuilder builder = new APIBuilder(this);
        builder.setCommandClasses(commands);

        //command is your /<command>, not subcommand.
        getCommand("prisonradio").setExecutor(builder.getCommandHandler());

    }

    @Override
    public void onDisable() {
        //sb.stopServer();
    }


}
