package me.Andrew.PrisonRadio.socketio.events;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import me.Andrew.PrisonRadio.PrisonRadio;
import me.Andrew.PrisonRadio.gui.ContolGUI;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by Andrew on 02/01/2018.
 */
public class controlsEvent implements DataListener<String> {
        @Override
        public void onData(SocketIOClient client, String s, AckRequest ackRequest) throws Exception {
            UUID mcuuid = (UUID) client.get("mcuuid");
            if (mcuuid == null) {
                return;
            }
            JSONObject data = new JSONObject(s);

            PrisonRadio main = PrisonRadio.getInstance();
            //main.getLogger().info(s);
            Player p = main.getServer().getPlayer(mcuuid);
            ContolGUI gui = new ContolGUI();

            p.openInventory(gui.getInv(data.getString("song"),data.getBoolean("paused")));
        }
}
