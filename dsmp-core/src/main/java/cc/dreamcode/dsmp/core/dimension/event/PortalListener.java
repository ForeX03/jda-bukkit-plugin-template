package cc.dreamcode.dsmp.core.dimension.event;

import cc.dreamcode.dsmp.core.config.MessageConfig;
import cc.dreamcode.dsmp.core.config.PluginConfig;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class PortalListener implements Listener {

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;

    @EventHandler
    public void onPortal(PlayerPortalEvent event) {
        if (event.getTo() == null) {
            return;
        }

        World targetWorld = event.getTo().getWorld();

        Player player = event.getPlayer();

        if (targetWorld == null) {
            return;
        }

        if (targetWorld.getName().equals("world_nether")) {
            if (this.pluginConfig.portalConfig.netherEnabled) {
                return;
            }

            this.messageConfig.portalNetherDisabled.send(player);
            event.setCancelled(true);
            return;
        }

        if (targetWorld.getName().equals("world_the_end")) {
            if (this.pluginConfig.portalConfig.endEnabled) {
                return;
            }

            this.messageConfig.portalEndDisabled.send(player);
            event.setCancelled(true);
        }
    }
}
