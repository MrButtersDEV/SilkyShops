package us.thezircon.play.silkyshops.menus.menus;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import us.thezircon.play.silkyshops.SilkyShops;
import us.thezircon.play.silkyshops.menus.Menu;
import us.thezircon.play.silkyshops.menus.PlayerMenuUtility;
import us.thezircon.play.silkyshops.menus.menuicons.SilkyShopIcons;

public class SilkyShop extends Menu {

    public SilkyShop(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return color("&bSilky&6&lShop");
    }

    @Override
    public InventoryType getType() {
        return InventoryType.HOPPER;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        // Buy Icon - Opens buy menu
        if (e.getCurrentItem().equals(SilkyShopIcons.buyIcon())) {
            new BuyMenu(SilkyShops.getPlayerMenuUtility(p)).open();
        }

        // Sell Icon - Opens sell menu
        if (e.getCurrentItem().equals(SilkyShopIcons.sellIcon())) {
            new SellMenu(SilkyShops.getPlayerMenuUtility(p)).open();
        }

        // Market Icon - Opens market menu
        if (e.getCurrentItem().equals(SilkyShopIcons.marketIcon())) {
            new MarketMenu(SilkyShops.getPlayerMenuUtility(p)).open();
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(0, SilkyShopIcons.buyIcon());
        inventory.setItem(2, SilkyShopIcons.sellIcon());
        inventory.setItem(4, SilkyShopIcons.marketIcon());
    }

    @Override
    public int getSlots() {
        return 0;
    }

    private static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
