package me.Andrew.PrisonRadio.commands;

import com.corundumstudio.socketio.SocketIOClient;
import me.Andrew.PrisonRadio.PrisonRadio;
import me.piggypiglet.pigapi.objects.CMD;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class controlsCommand extends CMD {

    public controlsCommand() {
        this.cmd = "controls";
    }

    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            SocketIOClient client = PrisonRadio.getInstance().sb.getClient(p);
            if (client == null) {
                p.sendMessage("your not on the link");
                return;
            }
            client.sendEvent("requestcontrols","");
            p.sendMessage(ChatColor.GOLD+"Requesting Controls");
        }
    }
}
