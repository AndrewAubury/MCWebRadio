package me.Andrew.PrisonRadio.events;

import com.corundumstudio.socketio.SocketIOClient;
import me.Andrew.PrisonRadio.PrisonRadio;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Andrew on 24/12/2017.
 */
public class LeaveEvent implements Listener{
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        SocketIOClient client = PrisonRadio.getInstance().sb.getClient(e.getPlayer());
        if(client != null){
            client.sendEvent("bad","Player left the server");
            client.sendEvent("play","");
        }
    }
}
