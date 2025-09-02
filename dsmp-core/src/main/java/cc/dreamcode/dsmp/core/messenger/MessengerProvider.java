package cc.dreamcode.dsmp.core.messenger;

import cc.dreamcode.platform.component.ComponentService;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MessengerProvider {

    private final ComponentService componentService;

    public void load() {
        this.componentService.registerComponent(MessengerCache.class);
        this.componentService.registerComponent(MessengerService.class);
    }
}
