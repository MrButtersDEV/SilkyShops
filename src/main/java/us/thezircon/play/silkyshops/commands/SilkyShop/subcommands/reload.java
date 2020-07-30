package us.thezircon.play.silkyshops.commands.SilkyShop.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.thezircon.play.silkyshops.SilkyShops;
import us.thezircon.play.silkyshops.commands.CMDManager;


import java.util.List;

public class reload extends CMDManager {

    SilkyShops plugin = SilkyShops.getPlugin(SilkyShops.class);

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "reloads plugin";
    }

    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("silkyshops.reload")) {
                plugin.reloadConfig();
                //player.sendMessage((msgPrefix + " " + msgReload));
                sender.sendMessage("Reloaded"); /////////////////////////////
            } else {
                //player.sendMessage(msgPrefix + " " + msgNoperm);
                sender.sendMessage("No Perms"); /////////////////////////////
            }
        } else {
            plugin.reloadConfig();
            sender.sendMessage("Reloaded"); /////////////////////////////
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
