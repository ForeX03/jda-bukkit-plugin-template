package cc.dreamcode.dsmp.discord.jda.sender;

import cc.dreamcode.dsmp.discord.jda.sender.message.BotMessage;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.Map;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BotSender {
    private final JDA jda;

    public void send(String channelId, BotMessage botMessage) {
        TextChannel channel = this.jda.getTextChannelById(channelId);

        if (channel == null) {
            return;
        }

        this.send(channel, botMessage);
    }
    public void send(String channelId, BotMessage botMessage, Map<String, Object> replaceMap) {
        TextChannel channel = this.jda.getTextChannelById(channelId);

        if (channel == null) {
            return;
        }

        this.send(channel, botMessage, replaceMap);
    }

    public void send(@NonNull TextChannel channel, BotMessage botMessage, Map<String, Object> replaceMap) {
        this.send(channel, botMessage.with(replaceMap));
    }

    public void send(@NonNull TextChannel channel, BotMessage botMessage) {
        if (!botMessage.isEnabled()) {
            return;
        }

        channel.sendMessage(botMessage.getText()).queue(message -> {
            if (botMessage.getDeleteDelay() <= 0) {
                return;
            }

            message.delete().queueAfter(botMessage.getDeleteDelay(), botMessage.getDeleteTimeUnit());
        });
    }

    public void reply(Message replyTo, BotMessage botMessage, Map<String, Object> replaceMap) {
        this.reply(replyTo, botMessage.with(replaceMap));
    }

    public void reply(Message replyTo, BotMessage botMessage) {
        if (!botMessage.isEnabled()) {
            return;
        }

        replyTo.reply(botMessage.getText()).queue(message -> {
            if (botMessage.getDeleteDelay() <= 0) {
                return;
            }

            message.delete().queueAfter(botMessage.getDeleteDelay(), botMessage.getDeleteTimeUnit());
        });
    }
}
