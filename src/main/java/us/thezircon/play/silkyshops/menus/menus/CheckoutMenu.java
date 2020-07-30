package us.thezircon.play.silkyshops.menus.menus;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.yaml.snakeyaml.error.YAMLException;
import us.thezircon.play.silkyshops.SilkyShops;
import us.thezircon.play.silkyshops.menus.Menu;
import us.thezircon.play.silkyshops.menus.PlayerMenuUtility;
import us.thezircon.play.silkyshops.utils.MarketSpawner;
import us.thezircon.play.silkyspawnerslite.SilkySpawnersLITE;
import us.thezircon.play.silkyspawnerslite.utils.SpawnerGiver;

import java.io.File;
import java.text.DecimalFormat;
import java.util.UUID;

public class CheckoutMenu extends Menu {

    public CheckoutMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    private static final SilkyShops plugin = SilkyShops.getPlugin(SilkyShops.class);

    @Override
    public String getMenuName() {
        return color("&2&lCheckout");
    }

    @Override
    public InventoryType getType() {
        return InventoryType.HOPPER;
    }

    @Override
    public int getSlots() {
        return 0;
    }

    private DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getCurrentItem().getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
            ItemStack sellIcon = e.getInventory().getItem(2);
            String checkoutType = SilkySpawnersLITE.getNMS().get("checkoutType", sellIcon);
            if (checkoutType.equals("BUY") || checkoutType.equals("SELL")) {
                String type = SilkySpawnersLITE.getNMS().get("type", sellIcon);
                double price = Double.valueOf(SilkySpawnersLITE.getNMS().get("price", sellIcon));
                if (checkoutType.equals("BUY")) {
                    EconomyResponse r = SilkyShops.getEconomy().withdrawPlayer(player, price);
                    if(r.transactionSuccess()) {
                        player.sendMessage(ChatColor.GREEN + "You bought " + sellIcon.getItemMeta().getDisplayName() + ChatColor.GREEN +  " for $"+df.format(price));
                        SpawnerGiver sg = new SpawnerGiver(EntityType.valueOf(type));
                        sg.give(player, false);
                        player.closeInventory();
                    } else {
                        player.sendMessage(ChatColor.RED + "Unable to buy spawner...");
                    }
                } else if (checkoutType.equals("SELL")) {
                    SpawnerGiver sg = new SpawnerGiver(EntityType.valueOf(type));

                    if (!(player.getInventory().containsAtLeast(sg.get(), 1))) {
                        player.sendMessage(ChatColor.RED+" You don't have any " + type + " spawners to sell.");
                        return;
                    }

                    EconomyResponse r = SilkyShops.getEconomy().depositPlayer(player, price);
                    if(r.transactionSuccess()) {

                        for (ItemStack items : player.getInventory().getContents()) {
                            if (sg.get().isSimilar(items)) {
                                items.setAmount(items.getAmount()-1);
                                player.updateInventory();
                            }
                        }

                        player.sendMessage(ChatColor.GREEN + "You sold " + sellIcon.getItemMeta().getDisplayName() + ChatColor.GREEN +  " for $"+df.format(price));
                        player.closeInventory();
                    } else {
                        player.sendMessage(ChatColor.RED + "Unable to sell spawner...");
                    }
                }
            } else if (checkoutType.equals("MARKET")) { ////////// Market Shop \\\\\\\\\\\\\\\\\\
                double price = Double.valueOf(SilkySpawnersLITE.getNMS().get("price", sellIcon));
                UUID seller = UUID.fromString(SilkySpawnersLITE.getNMS().get("seller", sellIcon));
                String id = SilkySpawnersLITE.getNMS().get("id", sellIcon);
                File confFile = new File(plugin.getDataFolder()+File.separator+"MarketData"+File.separator, id);
                FileConfiguration conf = YamlConfiguration.loadConfiguration(confFile);
                EntityType type = EntityType.valueOf(conf.getString("SpawnerType"));

                if (confFile.exists()) {
                    SpawnerGiver sg = new SpawnerGiver(type);
                    EconomyResponse r = SilkyShops.getEconomy().withdrawPlayer(player, price);
                    OfflinePlayer sPlayer = Bukkit.getOfflinePlayer(seller);
                    EconomyResponse r2= null;
                    boolean offline = false;
                    boolean sucess;
                    if (sPlayer.isOnline()) {
                        r2 = SilkyShops.getEconomy().depositPlayer(sPlayer, price);
                        sucess = r2.transactionSuccess();
                    } else {
                        sucess = SilkyShops.getOfflinePayment().addBalance(seller, price);
                    }

                    if(r.transactionSuccess() && confFile.exists() && sucess) {

                        player.sendMessage(ChatColor.GREEN + "You bought " + sellIcon.getItemMeta().getDisplayName() + ChatColor.GREEN +  " for $"+df.format(price));
                        player.closeInventory();
                        confFile.delete();
                        sg.give(player, false);
                        SilkyShops.updateMarketSpawner();

                    } else {
                        player.sendMessage(ChatColor.RED + "Unable to buy spawner...");
                    }

                } else {
                    player.sendMessage(ChatColor.YELLOW + "That spawner has sold!");
                }

            }
        } else if (e.getCurrentItem().getType().equals(Material.RED_STAINED_GLASS_PANE)) {
            e.getWhoClicked().closeInventory();
        }
    }

    @Override
    public void setMenuItems() {

        ItemStack yes = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta yes_meta = yes.getItemMeta();
        yes_meta.setDisplayName(color("&a&lConfirm"));
        yes.setItemMeta(yes_meta);

        ItemStack no = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta no_meta = no.getItemMeta();
        no_meta.setDisplayName(color("&c&lCancel"));
        no.setItemMeta(no_meta);

        inventory.setItem(0, yes);
        inventory.setItem(1, yes);

        ItemStack icon = SilkyShops.checkoutItem.get(playerMenuUtility.getOwner());
        inventory.setItem(2, icon);

        inventory.setItem(3, no);
        inventory.setItem(4, no);
    }

    private static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
