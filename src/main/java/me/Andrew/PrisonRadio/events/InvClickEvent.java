package me.Andrew.PrisonRadio.events;

import com.corundumstudio.socketio.SocketIOClient;
import me.Andrew.PrisonRadio.PrisonRadio;
import me.Andrew.PrisonRadio.gui.Selector;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Andrew on 02/01/2018.
 */
public class InvClickEvent implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getClickedInventory() == null){
            return;
        }
        if(e.getClickedInventory().getName() == null){
            return;
        }
        if(e.getClickedInventory().getName().equalsIgnoreCase(ChatColor.DARK_GREEN+"Song Selection")) {
            Player p = (Player) e.getWhoClicked();
            ItemStack clicked = e.getCurrentItem();
            Selector s = Selector.getInstance();
            e.setCancelled(true);
            if (clicked.getItemMeta() == null) return;
            if (clicked.getItemMeta().getDisplayName() == null) return;

            String link = s.getLink(ChatColor.stripColor(clicked.getItemMeta().getDisplayName()));
            p.sendMessage(ChatColor.GOLD + "Requesting server to play: " + clicked.getItemMeta().getDisplayName());
            PrisonRadio.getInstance().sb.getClient(p).sendEvent("playaudio", link);
            p.closeInventory();

        }else if(e.getClickedInventory().getName().equalsIgnoreCase(ChatColor.GREEN+"Controls")) {
            Player p = (Player) e.getWhoClicked();
            ItemStack clicked = e.getCurrentItem();
            e.setCancelled(true);
            SocketIOClient client = PrisonRadio.getInstance().sb.getClient(p);

            if(ChatColor.stripColor(clicked.getItemMeta().getDisplayName()).equalsIgnoreCase("play")){
                client.sendEvent("play","");
                client.sendEvent("requestcontrols","");
            }

            if(ChatColor.stripColor(clicked.getItemMeta().getDisplayName()).equalsIgnoreCase("play")){
                client.sendEvent("stop","");
                client.sendEvent("requestcontrols","");
            }

            if(ChatColor.stripColor(clicked.getItemMeta().getDisplayName()).equalsIgnoreCase("pause")){
                client.sendEvent("pause","");
                client.sendEvent("requestcontrols","");
            }
        }else{
            return;
        }
    }
}
