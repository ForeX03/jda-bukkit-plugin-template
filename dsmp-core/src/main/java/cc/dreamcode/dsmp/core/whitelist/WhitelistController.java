package cc.dreamcode.dsmp.core.whitelist;

import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.dsmp.core.config.subconfig.WhitelistConfig;
import cc.dreamcode.utilities.StringUtil;
import cc.dreamcode.utilities.bukkit.StringColorUtil;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class WhitelistController implements Listener {

    private final WhitelistService whitelistService;
    private final PluginConfig pluginConfig;

    @EventHandler
    public void onPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event) {
        WhitelistConfig whitelistConfig = this.pluginConfig.whitelistConfig;

        String nickname = event.getName();

        if (!whitelistService.isEnabled()) {
            return;
        }

        if (whitelistService.isWhitelisted(nickname)) {
           return;
        }

        String message = StringColorUtil.fixColor(StringUtil.join(whitelistConfig.kick, "\n&r"));

        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, message);
    }
}
