package cc.dreamcode.dsmp.bukkit.listener;

import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.utilities.StringUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BukkitChatListener implements Listener {

    private final PluginConfig pluginConfig;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) { // <%1$s> %2$s

        String newFormat = StringUtil.replace(this.pluginConfig.chatConfig.chatFormat, MapBuilder.of("player", "%1$s", "message", "%2$s")); // wymagane do formatowania w bukkicie ;>

        event.setFormat(newFormat);
    }
}
