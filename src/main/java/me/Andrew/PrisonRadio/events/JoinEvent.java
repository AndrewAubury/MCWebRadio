package me.Andrew.PrisonRadio.events;

import com.corundumstudio.socketio.SocketIOClient;
import me.Andrew.PrisonRadio.PrisonRadio;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Andrew on 24/12/2017.
 */
public class JoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        PrisonRadio.getInstance().sb.login(e.getPlayer());
    }

}
