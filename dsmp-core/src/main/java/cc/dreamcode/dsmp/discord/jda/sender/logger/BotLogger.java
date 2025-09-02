package cc.dreamcode.dsmp.discord.jda.sender.logger;

import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.dsmp.discord.jda.sender.BotSender;
import cc.dreamcode.dsmp.discord.jda.sender.message.BotMessage;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BotLogger {

    private final PluginConfig pluginConfig;
    private final BotSender botSender;

    public void log(BotMessage botMessage) {
        this.botSender.send(this.pluginConfig.botConfig.logChannelId, botMessage);
    }

    public void log(BotMessage botMessage, Map<String, Object> replaceMap) {
        this.botSender.send(this.pluginConfig.botConfig.logChannelId, botMessage, replaceMap);
    }
}
