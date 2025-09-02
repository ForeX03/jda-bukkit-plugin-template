package cc.dreamcode.dsmp.core.whitelist;

import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.platform.component.ComponentService;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class WhitelistProvider {

    private final ComponentService componentService;
    private final PluginConfig pluginConfig;

    public void load() {
        if (!pluginConfig.whitelistConfig.enabled) {
            return;
        }

        this.componentService.registerComponent(WhitelistStorage.class);
        this.componentService.registerComponent(WhitelistService.class);
        this.componentService.registerComponent(WhitelistController.class);
    }
}
