package cc.dreamcode.dsmp.core.whitelist.command;

import cc.dreamcode.dsmp.core.config.MessageConfig;
import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.dsmp.core.config.subconfig.WhitelistConfig;
import cc.dreamcode.dsmp.core.whitelist.WhitelistService;
import cc.dreamcode.utilities.StringUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.StringColorUtil;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class WhitelistCommand extends ListenerAdapter {

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;
    private final WhitelistService whitelistService;
    private final Server server;
    private final Tasker tasker;
    private final JDA jda;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        WhitelistConfig whitelistConfig = this.pluginConfig.whitelistConfig;

        if (!event.getName().equals("wl")) {
            return;
        }

        if (event.getSubcommandName() == null) {
            event.reply("Niepoprawne użycie komendy").setEphemeral(true).queue();
            return;
        }

        String argument = event.getSubcommandName();

        if (argument.equalsIgnoreCase("on")) {
            if (this.whitelistService.isEnabled()) {
                event.reply(this.messageConfig.whitelistAlreadyEnabled).setEphemeral(true).queue();
                return;
            }

            this.whitelistService.setEnabled(event.getMember(), true);

            if (whitelistConfig.kickOnEnable) {
                this.tasker.newChain()
                        .sync(() ->
                                this.server.getOnlinePlayers().forEach(player -> {
                                    if (!this.whitelistService.isWhitelisted(player)) {
                                        String message = StringColorUtil.fixColor(StringUtil.join(whitelistConfig.enabledKick, "\n&r"));

                                        player.kickPlayer(message);
                                    }
                                }))
                        .execute();
            }

            event.reply(this.messageConfig.whitelistEnabled).setEphemeral(true).queue();
            return;
        }

        if (argument.equalsIgnoreCase("off")) {
            if (!this.whitelistService.isEnabled()) {
                event.reply(this.messageConfig.whitelistAlreadyDisabled).setEphemeral(true).queue();
                return;
            }

            this.whitelistService.setEnabled(event.getMember(), false);

            event.reply(this.messageConfig.whitelistDisabled).setEphemeral(true).queue();
            return;
        }

        if (argument.equalsIgnoreCase("embed")) {
            Button button = Button.success("whitelist-open-modal", whitelistConfig.buttonLabel);

            event.getChannel().sendMessageEmbeds(whitelistConfig.embed.build())
                    .addActionRow(button)
                    .queue();

            event.reply(this.messageConfig.embedSend).setEphemeral(true).queue();
            return;
        }

        if (argument.equalsIgnoreCase("add")) {
            String nick = event.getOption("nick", OptionMapping::getAsString);

            if (this.whitelistService.isWhitelisted(nick)) {
                event.reply(StringUtil.replace(this.messageConfig.cmdNickAlreadyAdded, MapBuilder.of("nick", nick))).setEphemeral(true).queue();
                return;
            }

            this.whitelistService.addPlayer(event.getMember(), nick);

            event.reply(StringUtil.replace(this.messageConfig.cmdNickAdded, MapBuilder.of("nick", nick))).setEphemeral(true).queue();
            return;
        }

        if (argument.equalsIgnoreCase("remove")) {
            String nick = event.getOption("nick", OptionMapping::getAsString);

            if (!this.whitelistService.isWhitelisted(nick)) {
                event.reply(StringUtil.replace(this.messageConfig.nickNotWhitelisted, MapBuilder.of("nick", nick))).setEphemeral(true).queue();
                return;
            }

            this.whitelistService.removePlayer(event.getMember(), nick);

            event.reply(StringUtil.replace(this.messageConfig.cmdNickRemoved, MapBuilder.of("nick", nick))).setEphemeral(true).queue();
        }
    }
}
