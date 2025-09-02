package cc.dreamcode.dsmp.core.discordbridge;

import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.dsmp.core.discordbridge.listener.BukkitChatListener;
import cc.dreamcode.dsmp.core.discordbridge.listener.DiscordChannelListener;
import cc.dreamcode.platform.component.ComponentService;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BridgeProvider {

    private final ComponentService componentService;
    private final PluginConfig pluginConfig;

    public void load() {
        if (!this.pluginConfig.bridgeConfig.enabled) {
            return;
        }

        this.componentService.registerComponent(BukkitChatListener.class);
        this.componentService.registerComponent(DiscordChannelListener.class);
    }
}
