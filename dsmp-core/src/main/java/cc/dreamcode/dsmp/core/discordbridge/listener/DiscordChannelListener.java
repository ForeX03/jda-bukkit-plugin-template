package cc.dreamcode.dsmp.core.discordbridge.listener;

import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.dsmp.core.config.subconfig.BridgeConfig;
import cc.dreamcode.dsmp.discord.util.DisplayNameUtil;
import cc.dreamcode.dsmp.shared.util.StringReplaceUtil;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.utilities.DateUtil;
import cc.dreamcode.utilities.StringUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.StringColorUtil;
import eu.okaeri.injector.annotation.Inject;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class DiscordChannelListener extends ListenerAdapter {

    @Inject
    private PluginConfig pluginConfig;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        if (!event.getChannel().getId().equals(this.pluginConfig.bridgeConfig.discordOptions.chatChannelId)) {
            return;
        }

        BridgeConfig bridgeConfig = this.pluginConfig.bridgeConfig;
        BridgeConfig.MinecraftOptions minecraftOptions = bridgeConfig.minecraftOptions;
        Message message = event.getMessage();
        Message referencedMessage = message.getReferencedMessage();
        boolean isReply = referencedMessage != null;
        String messageAuthorName = DisplayNameUtil.getDisplayName(event.getMember(), event.getAuthor());
        String replyPlaceholder = "";

        if (isReply) {
            String refMessageAuthorName = DisplayNameUtil.getDisplayName(referencedMessage.getMember(), referencedMessage.getAuthor());
            if (event.getMessage().getAuthor().getId().equals(referencedMessage.getAuthor().getId())) {
                refMessageAuthorName = messageAuthorName;
            }

            Map<String, Object> placeholders = MapBuilder.of("timestamp", DateUtil.format(referencedMessage.getTimeCreated().toInstant()),
                    "player", refMessageAuthorName,
                    "message", StringReplaceUtil.replace(referencedMessage.getContentDisplay(), minecraftOptions.replaceMap));

            replyPlaceholder = StringUtil.replace("<hover:show_text:'" + minecraftOptions.replyHover + "'>" + minecraftOptions.reply + "</hover>", placeholders);
        }

        String semiRawFormat = StringColorUtil.fixColor(minecraftOptions.format, MapBuilder.of("format", this.pluginConfig.chatConfig.chatFormat, "reply", replyPlaceholder));
        String formattedMessage = StringReplaceUtil.replace(message.getContentDisplay(), minecraftOptions.replaceMap);
        String format = StringUtil.replace(semiRawFormat, MapBuilder.of("player", messageAuthorName, "message", formattedMessage));

        BukkitNotice notice = BukkitNotice.chat(format);

        notice.sendBroadcast();
    }
}
