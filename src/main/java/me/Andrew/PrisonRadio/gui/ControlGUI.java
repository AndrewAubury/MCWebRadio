package me.Andrew.PrisonRadio.gui;

import com.corundumstudio.socketio.SocketIOClient;
import me.Andrew.PrisonRadio.PrisonRadio;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ControlGUI {

    public Inventory getInv(String song,boolean paused){

        PrisonRadio pr = PrisonRadio.getInstance();
        Inventory inv = pr.getServer().createInventory(null,27, ChatColor.GREEN+"Controls");

        if(song != "none"){
            inv.setItem(getSlot(2,2), getPausePlayItem(paused));
            inv.setItem(getSlot(2,3), renameItem(new ItemStack(Material.BARRIER,1),ChatColor.RED+"Stop"));

            inv.setItem(getSlot(2,7), skull("MHF_ArrowDown",ChatColor.GREEN+"Volume Down"));
            inv.setItem(getSlot(2,8), skull("MHF_ArrowUp",ChatColor.GREEN+"Volume Up"));
        }else{
            song = "Please select a song";
        }

        inv.setItem(getSlot(2,5), renameItem(new ItemStack(Material.RECORD_7,1),ChatColor.GREEN+song));



        inv = fillBlanks(inv);
        return inv;
    }


    private Inventory fillBlanks(Inventory inv){

        for(int i=0; i < (inv.getSize());i++){
            ItemStack is = inv.getItem(i);
            if(is == null || is.getType() == Material.AIR){
                ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE,1, (short) 14);
                glass = renameItem(glass,ChatColor.RED+" ");
                inv.setItem(i,glass);
            }
        }

        return inv;
    }

    private ItemStack renameItem(ItemStack is, String name){
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        is.setItemMeta(im);
       return is;
    }
    //(2,3)
    private int getSlot(int row, int col){
        int slot = 0;
        row = row - 1;
        slot = row * 9;
        col = col - 1;
        slot = slot + col;
        return slot;
    }
    private ItemStack skull(String player,String name){

        SkullMeta  meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
        meta.setOwner(player);
        //meta.setOwningPlayer(PrisonRadio.getInstance().getServer().getOfflinePlayer())
        meta.setDisplayName(name);
        ItemStack stack = new ItemStack(Material.SKULL_ITEM,1 , (byte)3);
        stack.setItemMeta(meta);

        return stack;
    }
    private ItemStack getPausePlayItem(boolean paused){
        ItemStack is;

        if(paused){
            is = new ItemStack(Material.INK_SACK, 1, (short) DyeColor.GRAY.getDyeData());
            is = renameItem(is, ChatColor.GRAY+"Play");
        }else{
            is = new ItemStack(Material.INK_SACK, 1, (short) DyeColor.LIME.getDyeData());
            is = renameItem(is, ChatColor.GREEN+"Pause");
        }
         return is;
    }
}
