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
        public void onData(SocketIOClient socketIOClient, String string, AckRequest ackRequest) {

            try {

                UUID mcuuid = (UUID) socketIOClient.get("mcuuid");
                if (mcuuid == null) {
                    return;
                }
                JSONObject jsonObject = new JSONObject(string);
                PrisonRadio main = PrisonRadio.getInstance();
                main.getLogger().info(string);
                //main.getLogger().info("Hiiiiii");
                Player p = main.getServer().getPlayer(mcuuid);
                p.sendMessage(ChatColor.GREEN + "Opening Controls GUI");
                ControlGUI gui = new ControlGUI();
                p.openInventory(gui.getInv(jsonObject.getString("song"), jsonObject.getString("paused").equalsIgnoreCase("true")));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
}
