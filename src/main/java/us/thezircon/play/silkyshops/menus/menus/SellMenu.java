package us.thezircon.play.silkyshops.menus.menus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import us.thezircon.play.silkyshops.SilkyShops;
import us.thezircon.play.silkyshops.menus.PaginatedMenu;
import us.thezircon.play.silkyshops.menus.PlayerMenuUtility;

public class SellMenu extends PaginatedMenu {

    SilkyShops plugin = SilkyShops.getPlugin(SilkyShops.class);

    public SellMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return color("&c&lSell Spawners");
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
                if (!((index + 1) >= SilkyShops.getSpawners().getSell().size())){
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


        if(SilkyShops.getSpawners().getSell() != null && !SilkyShops.getSpawners().getSell().isEmpty()) {
            for (int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if (index >= SilkyShops.getSpawners().getSell().size()) break;
                if (SilkyShops.getSpawners().getSell().get(index) != null) {
                    inventory.addItem(SilkyShops.getSpawners().getSell().get(index));
                }
            }
        }

    }

    private static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
