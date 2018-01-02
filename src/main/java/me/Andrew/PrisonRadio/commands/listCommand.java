package me.Andrew.PrisonRadio.commands;

import com.corundumstudio.socketio.SocketIOClient;
import me.Andrew.PrisonRadio.PrisonRadio;
import me.Andrew.PrisonRadio.gui.Selector;
import me.piggypiglet.pigapi.objects.CMD;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Andrew on 01/01/2018.
 */
public class listCommand extends CMD {

    public listCommand() {
        this.cmd="list";
    }

    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
                Player p = (Player) sender;
                SocketIOClient client = PrisonRadio.getInstance().sb.getClient(p);
                if (client == null) {
                    p.sendMessage("your not on the link");
                    return;
                }
                Selector.getInstance().openSelector(p);
                return;
            }
    }
}
