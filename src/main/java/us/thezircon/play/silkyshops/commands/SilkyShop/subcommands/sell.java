package us.thezircon.play.silkyshops.commands.SilkyShop.subcommands;

import javafx.print.PageLayout;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.thezircon.play.silkyshops.SilkyShops;
import us.thezircon.play.silkyshops.commands.CMDManager;
import us.thezircon.play.silkyshops.utils.MarketSpawner;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class sell extends CMDManager {

    SilkyShops plugin = SilkyShops.getPlugin(SilkyShops.class);

    @Override
    public String getName() {
        return "sell";
    }

    @Override
    public String getDescription() {
        return "Place a spawner on the spawner market";
    }

    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            SilkyShops.getMarketSpawner().sellSpawner(player, Double.valueOf(args[1]));

        } else {
            sender.sendMessage(ChatColor.RED+"You must be a player to use this command");
        }
    }

    @Override
    public List<String> arg1(Player player, String[] args) {
        return Arrays.asList("00.00", "100.00", "1000.00");
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
