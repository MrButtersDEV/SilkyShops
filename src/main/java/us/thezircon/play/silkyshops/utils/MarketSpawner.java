package us.thezircon.play.silkyshops.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.thezircon.play.silkyshops.SilkyShops;
import us.thezircon.play.silkyspawnerslite.SilkySpawnersLITE;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MarketSpawner {

    private ArrayList<ItemStack> marketSpawners = new ArrayList<>();

    private static final SilkyShops plugin = SilkyShops.getPlugin(SilkyShops.class);

    private DecimalFormat df = new DecimalFormat("0.00");

    public MarketSpawner(){
        File marketData = new File(plugin.getDataFolder()+File.separator+"MarketData"+File.separator);

        // Create market data folder if not found.
        if (!marketData.exists()) {
            marketData.mkdirs();
        }

        // Get list of spawners on load
        try {
            for (File f : marketData.listFiles()) {
                FileConfiguration conf = YamlConfiguration.loadConfiguration(f); // Load as yml
                ItemStack spawner_a = new ItemStack(Material.SPAWNER);
                ItemMeta meta = spawner_a.getItemMeta();
                meta.setDisplayName(ChatColor.AQUA+conf.getString("SpawnerType").replace("_", " ") + " Spawner");
                meta.setLore(Arrays.asList("", c("&3Price: &7$"+df.format(conf.getDouble("Price"))), c("&3Seller: &7" + conf.getString("Seller.IGN"))));
                spawner_a.setItemMeta(meta);

                ItemStack spawner_b = SilkySpawnersLITE.getNMS().set("price", spawner_a, conf.getDouble("Price")+"");
                ItemStack spawner_c = SilkySpawnersLITE.getNMS().set("seller", spawner_b, conf.getString("Seller.UUID"));
                ItemStack spawner_d = SilkySpawnersLITE.getNMS().set("id", spawner_c, f.getName());
                ItemStack spawner_e = SilkySpawnersLITE.getNMS().set("checkoutType", spawner_d, "MARKET");
                marketSpawners.add(spawner_e);
            }
        } catch (NullPointerException err) {
            System.out.println("No files/spawners found to load on the market.");
        }

    }

    public ArrayList<ItemStack> getSpawners(){
        return marketSpawners;
    }

    public void sellSpawner(Player player, double price) {
        String code = code();
        File spawnerFile = new File(plugin.getDataFolder()+File.separator+"MarketData"+File.separator, code+".yml");
        try {
            spawnerFile.createNewFile();
        } catch (IOException ignore) {}
        ItemStack hand = player.getInventory().getItemInMainHand();
        FileConfiguration spawnerConf = YamlConfiguration.loadConfiguration(spawnerFile);
        spawnerConf.set("SpawnerType", SilkySpawnersLITE.getNMS().get("SilkyMob", hand));
        spawnerConf.set("Price", price);
        spawnerConf.set("Seller.IGN", player.getName());
        spawnerConf.set("Seller.UUID", player.getUniqueId()+"");
        try {
            spawnerConf.save(spawnerFile);
            player.getInventory().remove(player.getInventory().getItemInMainHand());
            player.updateInventory();
            player.sendMessage(ChatColor.GREEN + "Placed a spawner on the market for $" + price);

            ItemStack spawner_a = new ItemStack(Material.SPAWNER);
            ItemMeta meta = spawner_a.getItemMeta();
            meta.setDisplayName(ChatColor.AQUA+SilkySpawnersLITE.getNMS().get("SilkyMob", hand) + " Spawner");
            meta.setLore(Arrays.asList("", c("&3Price: &7$"+df.format(price)), c("&3Seller: &7" + player.getName())));
            spawner_a.setItemMeta(meta);

            ItemStack spawner_b = SilkySpawnersLITE.getNMS().set("price", spawner_a, price+"");
            ItemStack spawner_c = SilkySpawnersLITE.getNMS().set("seller", spawner_b, player.getUniqueId().toString());
            ItemStack spawner_d = SilkySpawnersLITE.getNMS().set("id", spawner_c, code+".yml");
            marketSpawners.add(spawner_d);

        } catch (IOException err) {
            System.out.println("Issue placing spawner on market");
            player.sendMessage(ChatColor.RED + "Failed to sell spawner on market");
            return;
        }
    }

    private static String code(){
        Random r = new Random();
        StringBuilder code = new StringBuilder();

        for (int x=0; x<=7; x++) {
            if (r.nextBoolean()) {
                code.append(rC());
            } else {
                code.append(rI());
            }
        }

        return code.toString();
    }

    private static int rI(){
        Random r = new Random();
        return r.nextInt(10);
    }

    private static char rC(){
        Random r = new Random();
        return ((char)(r.nextInt(26) + 'a'));
    }

    private static String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
