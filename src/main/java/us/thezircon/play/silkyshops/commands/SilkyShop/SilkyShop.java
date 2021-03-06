package us.thezircon.play.silkyshops.commands.SilkyShop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import us.thezircon.play.silkyshops.commands.CMDManager;
import us.thezircon.play.silkyshops.commands.SilkyShop.subcommands.open;
import us.thezircon.play.silkyshops.commands.SilkyShop.subcommands.reload;
import us.thezircon.play.silkyshops.commands.SilkyShop.subcommands.sell;
import us.thezircon.play.silkyshops.commands.SilkyShop.subcommands.test;


import java.util.ArrayList;
import java.util.List;

public class SilkyShop implements TabExecutor {

    private ArrayList<CMDManager> subcommands = new ArrayList<>();

    public SilkyShop(){
        subcommands.add(new reload());
        subcommands.add(new open());
        //subcommands.add(new test());
        subcommands.add(new sell());
    }

    public ArrayList<CMDManager> getSubCommands(){
        return subcommands;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length > 0){
            for (int i = 0; i < getSubCommands().size(); i++){
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                    getSubCommands().get(i).perform(sender, args);
                }
            }
        } else {
           // help helpCmd = new help();
           // helpCmd.perform(sender, args);
        }

        return true;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (sender instanceof ConsoleCommandSender) {
            return null;
        }

        if (args.length == 1) {
            ArrayList<String> subcommandsArguments = new ArrayList<>();

            for (int i = 0; i < getSubCommands().size(); i++) {
                subcommandsArguments.add(getSubCommands().get(i).getName());
            }

            return subcommandsArguments;
        } else if (args.length == 2) {
            for (int i = 0; i < getSubCommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    return getSubCommands().get(i).arg1((Player) sender, args);
                }
            }
        } else if (args.length == 3) {
            for (int i = 0; i < getSubCommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    return getSubCommands().get(i).arg2((Player) sender, args);
                }
            }
        }
        return null;
    }

}
