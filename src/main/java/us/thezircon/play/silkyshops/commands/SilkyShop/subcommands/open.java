package us.thezircon.play.silkyshops.commands.SilkyShop.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.thezircon.play.silkyshops.SilkyShops;
import us.thezircon.play.silkyshops.commands.CMDManager;
import us.thezircon.play.silkyshops.menus.menus.SilkyShop;

import java.util.List;

public class open extends CMDManager {
    @Override
    public String getName() {
        return "open";
    }

    @Override
    public String getDescription() {
        return "Opens silky shop";
    }

    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player) {

            Player p = (Player) sender;

            //create the menu and then open it for the player
            new SilkyShop(SilkyShops.getPlayerMenuUtility(p)).open();

        }
    }

    @Override
    public List<String> arg1(Player player, String[] args) {
        return null;
    }

    @Override
    public List<String> arg2(Player player, String[] args) {
        return null;
    }

    @Override
    public List<String> arg3(Player player, String[] args) {
        return null;
    }
}
