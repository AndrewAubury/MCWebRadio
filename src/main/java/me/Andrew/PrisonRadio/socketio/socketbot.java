package me.Andrew.PrisonRadio.socketio;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import me.Andrew.PrisonRadio.PrisonRadio;
import me.Andrew.PrisonRadio.socketio.events.playingEvent;
import me.Andrew.PrisonRadio.socketio.events.stopEvent;
import me.piggypiglet.pigapi.handlers.ChatHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.UUID;

/**
 * Created by Andrew on 24/12/2017.
 */
public class socketbot {
    public SocketIOServer server;
    public socketbot(String hostname, int port){
        Configuration config = new Configuration();
        config.setHostname(hostname);
        config.setPort(port);
        server = new SocketIOServer(config);
        server.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient client) {
                InetSocketAddress socketAddress = (InetSocketAddress) client.getRemoteAddress();
                InetAddress inetAddress = socketAddress.getAddress();
                System.out.println("Connection from: "+inetAddress.getHostAddress());
                Player match = null;
                for(Player p : PrisonRadio.getInstance().getServer().getOnlinePlayers()){
                    if(p.getAddress().getAddress().getHostAddress().equalsIgnoreCase(inetAddress.getHostAddress())){
                        match = p;
                    }
                }
                if(match == null){
                    client.sendEvent("bad","There is no minecraft account logged in on the same ip as you");
                    client.disconnect();
                }else{
                    SocketIOClient cur = getClient(match);
                    if(cur != null){
                        cur.sendEvent("bad","You have logged in from another location");
                    }
                    client.set("mcuuid",match.getUniqueId());
                    client.joinRoom("loggedin");
                    client.sendEvent("good", "You could be: "+match.getName());
                }
            }
        });

        server.addEventListener("playing", String.class, new playingEvent());
        server.addEventListener("ended",String.class,new stopEvent());
    }


    public void startServer() {
        server.startAsync();
    }
    public void stopServer(){
        server.stop();
        server.getClient(null);
    }

    public void login(Player p){
        for(SocketIOClient client : server.getAllClients()){
            if(client.isChannelOpen()) {
                if (!client.getAllRooms().contains("loggedin")) {
                    InetSocketAddress socketAddress = (InetSocketAddress) client.getRemoteAddress();
                    InetAddress inetAddress = socketAddress.getAddress();

                    if (p.getAddress().getAddress().getHostAddress().equalsIgnoreCase(inetAddress.getHostAddress())) {
                        client.sendEvent("good", "You are now logged in on the server as: " + p.getName());
                        client.set("mcuuid", p.getUniqueId());
                        client.joinRoom("loggedin");
                    }
                }
            }
        }
    }

    public SocketIOClient getClient(Player p){
        for(SocketIOClient client : server.getAllClients()){
            if(client.isChannelOpen()) {
                if (client.getAllRooms().contains("loggedin")) {
                    UUID cuuid = (UUID) client.get("mcuuid");
                    if (cuuid != null && cuuid.equals(p.getUniqueId())) {
                        return client;
                    }
                }
            }
        }
        return null;
    }

}
