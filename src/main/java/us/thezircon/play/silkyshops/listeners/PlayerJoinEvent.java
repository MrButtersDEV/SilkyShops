package us.thezircon.play.silkyshops.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import us.thezircon.play.silkyshops.SilkyShops;
import us.thezircon.play.silkyshops.utils.OfflinePayment;

public class PlayerJoinEvent implements Listener {

    private static final SilkyShops plugin = SilkyShops.getPlugin(SilkyShops.class);

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent e) {
        Player player = e.getPlayer();

        // Claim any remaining balance from selling spawners on the market
        OfflinePayment offlinePayment = SilkyShops.getOfflinePayment();
        offlinePayment.claimBalance(player.getUniqueId());

    }

}
