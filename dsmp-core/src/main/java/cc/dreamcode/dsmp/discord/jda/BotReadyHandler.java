package cc.dreamcode.dsmp.discord.jda;

import cc.dreamcode.dsmp.DSMPCore;
import cc.dreamcode.platform.component.ComponentService;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BotReadyHandler extends ListenerAdapter {

    private final ComponentService componentService;
    private final Tasker tasker;

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        this.tasker.newChain()
                .sync(() -> DSMPCore.getInstance().onBotLoad(this.componentService))
                .execute();

    }
}
