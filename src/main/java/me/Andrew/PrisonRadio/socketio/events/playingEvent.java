package me.Andrew.PrisonRadio.socketio.events;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import me.Andrew.PrisonRadio.PrisonRadio;
import org.bukkit.ChatColor;

import java.util.UUID;

/**
 * Created by Andrew on 02/01/2018.
 */
public class playingEvent implements DataListener<String> {
    @Override
    public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
        //PrisonRadio.getInstance().getServer().broadcastMessage("Event called");
        //PrisonRadio.getInstance().getServer().broadcastMessage("Someone is playing: "+s);
        UUID mcuuid = (UUID) socketIOClient.get("mcuuid");
        if(mcuuid == null){
            return;
        }
        String msg = "&aNow playing: &2"+s;
        msg = ChatColor.translateAlternateColorCodes('&',msg);
        PrisonRadio.getInstance().getServer().getPlayer(mcuuid).sendMessage(msg);
    }
}
