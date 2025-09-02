package cc.dreamcode.dsmp.core.discordbridge.listener;

import cc.dreamcode.dsmp.DSMPCore;
import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.dsmp.core.config.subconfig.BridgeConfig;
import cc.dreamcode.dsmp.discord.webhook.DiscordWebhook;
import cc.dreamcode.dsmp.shared.util.StringReplaceUtil;
import cc.dreamcode.utilities.StringUtil;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BukkitChatListener implements Listener {

    private final PluginConfig pluginConfig;

    private final Tasker tasker;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        BridgeConfig bridgeConfig = this.pluginConfig.bridgeConfig;
        BridgeConfig.DiscordOptions discordOptions = bridgeConfig.discordOptions;

        Player player = event.getPlayer();
        String message = StringReplaceUtil.replace(event.getMessage(), discordOptions.replaceMap);

        DiscordWebhook webhook = new DiscordWebhook(discordOptions.webhookUrl);
        webhook.setAvatarUrl(StringUtil.replace(discordOptions.avatarUrl, "player", player.getName()));
        webhook.setUsername(StringUtil.replace(discordOptions.name, "player", player.getName()));
        webhook.setContent(StringUtil.replace(discordOptions.format, "message", message));

        this.tasker.newChain()
                .async(() -> {
                    try {
                        webhook.execute();
                    }
                    catch (IOException e) {
                        DSMPCore.getInstance().getDreamLogger().error("Wystapil blad przy wysylaniu danych do webhooka: " + e.getMessage());
                    }
                })
                .execute();
    }
}
