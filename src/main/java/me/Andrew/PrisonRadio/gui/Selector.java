package me.Andrew.PrisonRadio.gui;

import me.Andrew.PrisonRadio.PrisonRadio;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.google.common.net.HttpHeaders.USER_AGENT;

/**
 * Created by Andrew on 02/01/2018.
 */
public class Selector {
    Inventory inv = null;
    HashMap<String,String> namePathLink;

    public static Selector thisinstance;

    public static Selector getInstance(){
        return thisinstance;
    }

    public Selector(){
        thisinstance = this;
        namePathLink = new HashMap<>();
    }


    public void openSelector(Player p){
        if(inv == null){
            setInv();
        }
        p.openInventory(inv);
    }

    private void addLink(String name,String path){
        namePathLink.put(name,path);
    }

    public String getLink(String name){
        return namePathLink.get(name);
    }

    private void setInv(){
        JSONArray arr = null;
        try {
            arr = new JSONArray(getMusicJSON());
        } catch (Exception e) {
            e.printStackTrace();
        }

        inv = PrisonRadio.getInstance().getServer().createInventory(null,getInventorySize(arr.length()),ChatColor.DARK_GREEN+"Song Selection");

        for (int i = 0; i < arr.length(); i++) {
            JSONObject object = arr.getJSONObject(i);
            ItemStack is = new ItemStack(Material.RECORD_6);
            ItemMeta im = is.getItemMeta();

            String name = ChatColor.GREEN + object.getString("title");

            ArrayList<String> lore = new ArrayList<>();

            lore.add(ChatColor.DARK_GREEN + "Duration: "+ChatColor.GREEN+object.getString("formatted_time"));

            im.setDisplayName(name);
            im.setLore(lore);
            is.setItemMeta(im);
            inv.addItem(is);

            addLink(object.getString("title"),object.getString("path"));
        }
    }
    private int getInventorySize(int max) {
        if (max <= 0) return 9;
        int quotient = (int)Math.ceil(max / 9.0);
        return quotient > 5 ? 54: quotient * 9;
    }

    private String getMusicJSON() throws Exception {
        String url = PrisonRadio.getInstance().getConfig().getString("apibase")+"getall.php";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", USER_AGENT);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return(response.toString());
    }
}
