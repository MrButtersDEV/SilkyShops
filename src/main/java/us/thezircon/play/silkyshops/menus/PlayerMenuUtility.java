package us.thezircon.play.silkyshops.menus;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/*
Companion class to all menus. This is needed to pass information across the entire
 menu system no matter how many inventories are opened or closed.

 Each player has one of these objects, and only one.
 */

public class PlayerMenuUtility {


    private Player owner;
    //store the player that will be killed so we can access him in the next menu

    public PlayerMenuUtility(Player p) {
        this.owner = p;
    }


    public Player getOwner() {
        return owner;
    }

}
