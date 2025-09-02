package cc.dreamcode.dsmp.discord.command;

import cc.dreamcode.dsmp.core.config.MessageConfig;
import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.dsmp.discord.jda.embed.DreamEmbed;
import cc.dreamcode.utilities.StringUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class DiscordDSMPCommand extends ListenerAdapter {

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("dsmp")) {
            return;
        }

        if (event.getSubcommandName() == null) {
            event.reply("Niepoprawne użycie komendy").setEphemeral(true).queue();
            return;
        }

        String argument = event.getSubcommandName();

        if (argument.equalsIgnoreCase("send-embed")) {
            String embedId = event.getOption("embed-id", OptionMapping::getAsString);
            Channel channel = event.getOption("channel", OptionMapping::getAsChannel);

            if (channel == null) {
                channel = event.getChannel();
            }

            if (!this.pluginConfig.embedMap.containsKey(embedId)) {
                event.reply(this.messageConfig.embedNotFound).setEphemeral(true).queue();
                return;
            }

            if (!(channel instanceof MessageChannel)) {
                event.reply(this.messageConfig.mustBeTextChannel).setEphemeral(true).queue();
                return;
            }

            DreamEmbed dreamEmbed = this.pluginConfig.embedMap.get(embedId);

            ((MessageChannel) channel).sendMessageEmbeds(dreamEmbed.build()).queue();

            event.reply(this.messageConfig.embedSend).setEphemeral(true).queue();
            return;
        }

        if (argument.equalsIgnoreCase("embed-list")) {
            String embedList = StringUtil.join(new ArrayList<>(this.pluginConfig.embedMap.keySet()), ", ");

            event.reply(StringUtil.replace(this.messageConfig.embedList, MapBuilder.of("list", embedList))).queue();
            return;
        }

        event.reply("Niepoprawne użycie komendy").setEphemeral(true).queue();
    }
}
