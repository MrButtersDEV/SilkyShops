package us.thezircon.play.silkyshops.utils;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import us.thezircon.play.silkyshops.SilkyShops;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class OfflinePayment {

    private static final SilkyShops plugin = SilkyShops.getPlugin(SilkyShops.class);

    private static YamlConfiguration conf;
    private static File OfflinePaymentData;

    public OfflinePayment() {
        OfflinePaymentData = new File(plugin.getDataFolder()+File.separator+"OfflinePaymentData.yml");

        // Create market data folder if not found.
        if (!OfflinePaymentData.exists()) {
            try {
                OfflinePaymentData.createNewFile();
            } catch (IOException er) {
                System.out.println("Issue creating offlinepaymentdata.yml");
            }
        }

        conf = YamlConfiguration.loadConfiguration(OfflinePaymentData);
    }

    public double getBalance(UUID uuid) {
        if (hasBalance(uuid)) {
            return conf.getDouble(uuid.toString());
        }
        return 0.00;
    }

    public boolean hasBalance(UUID uuid) {
        return conf.getKeys(true).contains(uuid.toString());
    }

    public boolean addBalance(UUID uuid, double amt) {
        if (hasBalance(uuid)) {
            conf.set(uuid.toString(), getBalance(uuid)+amt);
        } else {
            conf.set(uuid.toString(), amt);
        }

        try {
            conf.save(OfflinePaymentData);
            return true;
        } catch (IOException err) {
            return false;
        }
    }

    public void claimBalance(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        double bal = getBalance(uuid);
        if (bal>0.0) {
            EconomyResponse r = SilkyShops.getEconomy().depositPlayer(player, bal);
            if (r.transactionSuccess()) {
                player.sendMessage(String.format(ChatColor.GREEN+"You received %s and now have %s from the spawner market", SilkyShops.getEconomy().format(r.amount), SilkyShops.getEconomy().format(r.balance)));
                conf.set(uuid.toString(), null);
                try {
                    conf.save(OfflinePaymentData);
                } catch (IOException err) {
                    System.out.println("////////// Failed to clear claimed balance for UUID: " + uuid.toString() + " \\\\\\\\\\\\\\\\\\\\");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Failed to claim balance from sold spawners...");
            }
        }
    }

}
