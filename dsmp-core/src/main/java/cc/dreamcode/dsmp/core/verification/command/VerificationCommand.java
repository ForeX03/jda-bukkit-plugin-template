package cc.dreamcode.dsmp.core.verification.command;

import cc.dreamcode.dsmp.core.config.MessageConfig;
import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.dsmp.core.config.subconfig.VerificationConfig;
import cc.dreamcode.dsmp.discord.jda.sender.logger.BotLogger;
import cc.dreamcode.dsmp.discord.jda.sender.message.BotMessage;
import cc.dreamcode.utilities.StringUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;


@RequiredArgsConstructor(onConstructor_ = @Inject)
public class VerificationCommand extends ListenerAdapter {

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;
    private final BotLogger botLogger;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        VerificationConfig verificationConfig = this.pluginConfig.verificationConfig;

        if (!event.getName().equals("verify")) {
            return;
        }

        if (event.getSubcommandName() == null) {
            event.reply("Niepoprawne użycie komendy").setEphemeral(true).queue();
            return;
        }

        String argument = event.getSubcommandName();

        if (argument.equalsIgnoreCase("send-embed")) {
            Button button = Button.primary("verification-open-modal", verificationConfig.buttonLabel);

            event.getChannel().sendMessageEmbeds(verificationConfig.embed.build())
                    .addActionRow(button)
                    .queue();

            event.reply(this.messageConfig.embedSend).setEphemeral(true).queue();
            return;
        }

        if (argument.equalsIgnoreCase("user")) {
            Member member = event.getOption("user", OptionMapping::getAsMember);

            if (member == null) {
                event.reply(this.messageConfig.memberNotFound).setEphemeral(true).queue();
                return;
            }

            if (member.getRoles().stream().anyMatch(role -> role.getId().equals(verificationConfig.roleId))) {
                event.reply(StringUtil.replace(this.messageConfig.userVerifiedYet, MapBuilder.of("member", member.getAsMention()))).setEphemeral(true).queue();
                return;
            }

            try {
                event.getGuild().addRoleToMember(member, event.getGuild().getRoleById(verificationConfig.roleId)).queue();

                event.reply(this.messageConfig.userVerified).setEphemeral(true).queue();
            } catch (Exception e) {
                this.botLogger.log(new BotMessage("Niepowodzenie przy nadawaniu roli użytkownikowi {member}" + e.getMessage()).with(MapBuilder.of("member", member.getAsMention())));
            }
        }
    }
}
