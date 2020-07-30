package us.thezircon.play.silkyshops.menus.menus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import us.thezircon.play.silkyshops.SilkyShops;
import us.thezircon.play.silkyshops.menus.PaginatedMenu;
import us.thezircon.play.silkyshops.menus.PlayerMenuUtility;
import us.thezircon.play.silkyshops.utils.Spawners;

import java.util.ArrayList;

public class BuyMenu extends PaginatedMenu {

    private static final SilkyShops plugin = SilkyShops.getPlugin(SilkyShops.class);

    public BuyMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return color("&a&lBuy Spawners");
    }

    @Override
    public InventoryType getType() {
        return InventoryType.CHEST;
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getCurrentItem().getType().equals(Material.SPAWNER)) {
            SilkyShops.checkoutItem.put(p, e.getCurrentItem());
            new CheckoutMenu(SilkyShops.getPlayerMenuUtility(p)).open();
            return;
        }

        if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
        //close inventory
        p.closeInventory();
        }else if(e.getCurrentItem().getType().equals(Material.DARK_OAK_BUTTON)){
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Left")){
                if (page == 0){
                    p.sendMessage(ChatColor.GRAY + "You are already on the first page.");
                }else{
                    page = page - 1;
                    super.open();
                }
            }else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Right")){
                if (!((index + 1) >= SilkyShops.getSpawners().getBuy().size())){
                    page = page + 1;
                    super.open();
                }else{
                    p.sendMessage(ChatColor.GRAY + "You are on the last page.");
                }
            }
        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();


        if(SilkyShops.getSpawners().getBuy() != null && !SilkyShops.getSpawners().getBuy().isEmpty()) {
            for (int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if (index >= SilkyShops.getSpawners().getBuy().size()) break;
                if (SilkyShops.getSpawners().getBuy().get(index) != null) {
                    inventory.addItem(SilkyShops.getSpawners().getBuy().get(index));
                }
            }
        }

        //for (ItemStack items : SilkyShops.getSpawners().getBuy()) {
        //    inventory.addItem(items);
        //}

    }

    private static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
