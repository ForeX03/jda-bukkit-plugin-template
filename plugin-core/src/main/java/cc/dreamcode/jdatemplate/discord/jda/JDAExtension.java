package cc.dreamcode.jdatemplate.discord.jda;

import cc.dreamcode.jdatemplate.core.config.PluginConfig;
import cc.dreamcode.jdatemplate.discord.jda.handler.BotReadyHandler;
import cc.dreamcode.jdatemplate.discord.jda.resolver.JDAListenerAdapterResolver;
import cc.dreamcode.platform.bukkit.DreamBukkitPlatform;
import cc.dreamcode.platform.component.ComponentExtension;
import cc.dreamcode.platform.component.ComponentService;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class JDAExtension implements ComponentExtension {

    private final PluginConfig pluginConfig;
    private final DreamBukkitPlatform platform;

    @Override
    public void register(@NonNull ComponentService componentService) {
        JDA jda = JDABuilder.createLight(this.pluginConfig.botConfig.botToken, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MODERATION, GatewayIntent.MESSAGE_CONTENT)
                .build();

        this.platform.registerInjectable(jda);
        componentService.registerResolver(JDAListenerAdapterResolver.class);
        componentService.registerComponent(BotReadyHandler.class);
    }
}
