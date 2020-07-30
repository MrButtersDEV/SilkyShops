package us.thezircon.play.silkyshops.menus.menuicons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SilkyShopIcons {

    public static ItemStack buyIcon(){
        ItemStack icon = new ItemStack(Material.BEACON);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(color("&a&lBuy Spawners"));

        icon.setItemMeta(meta);
        return icon;
    }

    public static ItemStack sellIcon(){
        ItemStack icon = new ItemStack(Material.END_PORTAL_FRAME);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(color("&c&lSell Spawners"));

        icon.setItemMeta(meta);
        return icon;
    }

    public static ItemStack marketIcon(){
        ItemStack icon = new ItemStack(Material.ENCHANTING_TABLE);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(color("&5&lSpawner Market"));

        icon.setItemMeta(meta);
        return icon;
    }

    private static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
