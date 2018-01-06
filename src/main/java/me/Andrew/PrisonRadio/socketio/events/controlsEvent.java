package me.Andrew.PrisonRadio.socketio.events;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import me.Andrew.PrisonRadio.PrisonRadio;
import me.Andrew.PrisonRadio.gui.ControlGUI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by Andrew on 02/01/2018.
 */
public class controlsEvent implements DataListener<String> {
        @Override
        public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) {
            UUID mcuuid = (UUID) socketIOClient.get("mcuuid");
            if (mcuuid == null) {
                return;
            }
            JSONObject jsonObject = new JSONObject(s);

            PrisonRadio main = PrisonRadio.getInstance();
            //main.getLogger().info(s);
            //main.getLogger().info("Hiiiiii");
            main.getLogger().info(jsonObject.getString("song")+" | "+jsonObject.getString("paused"));

            Player p = main.getServer().getPlayer(mcuuid);

            main.getLogger().info(jsonObject.getString("song")+" | "+jsonObject.getString("paused"));

            p.sendMessage(ChatColor.GREEN+"Opening Controls GUI");
            p.openInventory(ControlGUI.getInv(jsonObject.getString("song"),jsonObject.getString("paused").equalsIgnoreCase("true")));
        }
}
