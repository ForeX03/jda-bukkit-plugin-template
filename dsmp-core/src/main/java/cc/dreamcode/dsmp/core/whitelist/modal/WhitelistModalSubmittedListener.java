package cc.dreamcode.dsmp.core.whitelist.modal;

import cc.dreamcode.dsmp.core.config.MessageConfig;
import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.dsmp.core.config.subconfig.WhitelistConfig;
import cc.dreamcode.dsmp.core.profile.ProfileCache;
import cc.dreamcode.dsmp.core.profile.ProfileController;
import cc.dreamcode.dsmp.core.whitelist.WhitelistService;
import cc.dreamcode.dsmp.discord.jda.sender.logger.BotLogger;
import cc.dreamcode.dsmp.discord.jda.sender.message.BotMessage;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class WhitelistModalSubmittedListener extends ListenerAdapter {

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;
    private final WhitelistService whitelistService;
    private final BotLogger botLogger;
    private final ProfileCache profileCache;
    private final ProfileController profileController;

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        WhitelistConfig whitelistConfig = this.pluginConfig.whitelistConfig;

        if (!event.getModalId().equals("whitelist-modal")) {
            return;
        }

        ModalMapping nickMapping = event.getValue("whitelist-modal-nick");

        if (nickMapping == null) {
            event.reply("Błąd wewnętrzny. Zgłoś do administracji").setEphemeral(true).queue();
            return;
        }

        String nick = nickMapping.getAsString();

        if (whitelistService.isWhitelisted(nick)) {
            event.reply(this.messageConfig.nickAlreadyAdded.replace("{nick}", nick)).setEphemeral(true).queue();
            return;
        }

        if (nick.isEmpty()) {
            event.reply("Błąd wewnętrzny. Zgłoś do administracji").setEphemeral(true).queue();
            return;
        }

        this.whitelistService.addPlayer(event.getMember(), nick);

        event.reply(this.messageConfig.nickAdded.replace("{nick}", nick)).setEphemeral(true).queue();

        if (!whitelistConfig.roleId.isEmpty()) {
            try {
                Role role = event.getGuild().getRoleById(whitelistConfig.roleId);

                event.getGuild().addRoleToMember(event.getMember(), role).queue();
            } catch (Exception e) {
                this.botLogger.log(new BotMessage("Niepowodzenie przy nadawaniu roli użytkownikowi {member} ({nick})" + e.getMessage()).with(MapBuilder.of("member", "<@" + event.getMember().getId() + ">", "nick", nick)));
            }
        }

        if (whitelistConfig.renameUsers) {
            try {
                event.getGuild().modifyNickname(event.getMember(), nick).queue();
            } catch (Exception e) {
                this.botLogger.log(new BotMessage("Niepowodzenie przy zmianie nicku użytkownika {member} ({nick}): " + e.getMessage()).with(MapBuilder.of("member", "<@" + event.getMember().getId() + ">", "nick", nick)));
            }
        }
    }
}
