package us.thezircon.play.silkyshops;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import us.thezircon.play.silkyshops.commands.SilkyShop.SilkyShop;
import us.thezircon.play.silkyshops.listeners.MenuListener;
import us.thezircon.play.silkyshops.listeners.PlayerJoinEvent;
import us.thezircon.play.silkyshops.menus.PlayerMenuUtility;
import us.thezircon.play.silkyshops.utils.MarketSpawner;
import us.thezircon.play.silkyshops.utils.Metrics;
import us.thezircon.play.silkyshops.utils.OfflinePayment;
import us.thezircon.play.silkyshops.utils.Spawners;


import java.util.HashMap;
import java.util.logging.Logger;

public final class SilkyShops extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    private static Spawners spawners = null;
    private static MarketSpawner marketSpawner = null;
    private static OfflinePayment offlinePayment = null;
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
    public static HashMap<Player, ItemStack> checkoutItem = new HashMap<>();

    @Override
    public void onEnable() {
        // Check for Silky Spawners
        if (getServer().getPluginManager().getPlugin("SilkySpawnersLITE")==null) {
            log.severe(String.format("[%s] - Disabled due to not having SilkySpawnersLITE installed!", getDescription().getName()));
            log.info("https://www.spigotmc.org/resources/76103/");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Check for Vault
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Setup config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        spawners = new Spawners();
        marketSpawner = new MarketSpawner();
        offlinePayment = new OfflinePayment();

        // bStats Metrics
        Metrics metrics = new Metrics(this, 7902);

        // Setup commands
        getCommand("silkyshop").setExecutor(new SilkyShop());

        //listener system
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this);

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Spawners getSpawners() {
        return spawners;
    }

    public static OfflinePayment getOfflinePayment() {
        return offlinePayment;
    }

    public static MarketSpawner getMarketSpawner() {
        return marketSpawner;
    }

    public static void updateMarketSpawner() {
        marketSpawner = new MarketSpawner();
    }

    //Provide a player and return a menu system for that player
    //create one if they don't already have one
    public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
        PlayerMenuUtility playerMenuUtility;
        if (!(playerMenuUtilityMap.containsKey(p))) { //See if the player has a playermenuutility "saved" for them

            //This player doesn't. Make one for them add add it to the hashmap
            playerMenuUtility = new PlayerMenuUtility(p);
            playerMenuUtilityMap.put(p, playerMenuUtility);

            return playerMenuUtility;
        } else {
            return playerMenuUtilityMap.get(p); //Return the object by using the provided player
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
