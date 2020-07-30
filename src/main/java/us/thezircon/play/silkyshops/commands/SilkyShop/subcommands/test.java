package us.thezircon.play.silkyshops.commands.SilkyShop.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.thezircon.play.silkyshops.SilkyShops;
import us.thezircon.play.silkyshops.commands.CMDManager;

import java.util.List;
import java.util.Random;

public class test extends CMDManager{

    SilkyShops plugin = SilkyShops.getPlugin(SilkyShops.class);

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getDescription() {
        return "test command";
    }

    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
          //  Player player = (Player) sender;
          //  SpawnerGiver spawner = new SpawnerGiver(EntityType.COW);
           // spawner.giveSelf(player);

            Random r = new Random();
            char c = (char)(r.nextInt(26) + 'a');
            int i = r.nextInt(10);
            System.out.println("Random Code: " + c+i);

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
