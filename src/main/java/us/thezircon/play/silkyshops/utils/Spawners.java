package us.thezircon.play.silkyshops.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.thezircon.play.silkyshops.SilkyShops;
import us.thezircon.play.silkyspawnerslite.SilkySpawnersLITE;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Spawners {

    private static final SilkyShops plugin = SilkyShops.getPlugin(SilkyShops.class);

    private ArrayList<String> buy = new ArrayList<>();
    private ArrayList<String> sell = new ArrayList<>();
    private DecimalFormat f = new DecimalFormat("0.00");

    public Spawners() {

        Set prices = plugin.getConfig().getConfigurationSection("Prices").getKeys(false);

        for (Object key : prices){
            EntityType type = null;
            try {
                type = EntityType.valueOf(key.toString());
                if (plugin.getConfig().contains("Prices."+key.toString()+".Sell")) {
                    this.sell.add(key.toString());
                }
                if (plugin.getConfig().contains("Prices."+key.toString()+".Buy")) {
                    this.buy.add(key.toString());
                }
            } catch (NullPointerException ignored) {}
        }
    }

    public ArrayList<ItemStack> getBuy() {
        ArrayList<ItemStack> buyList = new ArrayList<>();
        for (String s : buy) {
            ItemStack spawner = new ItemStack(Material.SPAWNER);
            ItemMeta meta = spawner.getItemMeta();
            meta.setDisplayName(ChatColor.AQUA + s.replace("_", " ") + " Spawner");
            meta.setLore(Arrays.asList("", ChatColor.translateAlternateColorCodes('&', "&3Price: &7$" + f.format(plugin.getConfig().getDouble("Prices."+s+".Buy")))));
            spawner.setItemMeta(meta);
            ItemStack s1 = SilkySpawnersLITE.getNMS().set("price", spawner, plugin.getConfig().getDouble("Prices."+s+".Buy")+"");
            ItemStack s2 = SilkySpawnersLITE.getNMS().set("type", s1, s);
            ItemStack s3 = SilkySpawnersLITE.getNMS().set("checkoutType", s2, "BUY");
            buyList.add(s3);
        }
        return buyList;
    }

    public ArrayList<ItemStack> getSell() {
        ArrayList<ItemStack> sellList = new ArrayList<>();
        for (String s : sell) {
            ItemStack spawner = new ItemStack(Material.SPAWNER);
            ItemMeta meta = spawner.getItemMeta();
            meta.setDisplayName(ChatColor.AQUA + s.replace("_", " ") + " Spawner");
            meta.setLore(Arrays.asList("", ChatColor.translateAlternateColorCodes('&', "&3Sell Price: &7$" + f.format(plugin.getConfig().getDouble("Prices."+s+".Sell")))));
            spawner.setItemMeta(meta);
            ItemStack s1 = SilkySpawnersLITE.getNMS().set("price", spawner, plugin.getConfig().getDouble("Prices."+s+".Sell")+"");
            ItemStack s2 = SilkySpawnersLITE.getNMS().set("type", s1, s);
            ItemStack s3 = SilkySpawnersLITE.getNMS().set("checkoutType", s2, "SELL");
            sellList.add(s3);
        }
        return sellList;
    }

}
