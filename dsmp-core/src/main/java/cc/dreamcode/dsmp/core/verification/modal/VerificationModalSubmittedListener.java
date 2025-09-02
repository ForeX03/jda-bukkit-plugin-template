package cc.dreamcode.dsmp.core.verification.modal;

import cc.dreamcode.dsmp.core.config.MessageConfig;
import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.dsmp.core.config.subconfig.VerificationConfig;
import cc.dreamcode.dsmp.discord.jda.sender.logger.BotLogger;
import cc.dreamcode.dsmp.discord.jda.sender.message.BotMessage;
import cc.dreamcode.utilities.ParseUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class VerificationModalSubmittedListener extends ListenerAdapter {

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;
    private final BotLogger botLogger;

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        VerificationConfig verificationConfig = this.pluginConfig.verificationConfig;

        if (!event.getModalId().startsWith("verification-modal_")) {
            return;
        }

        Optional<Integer> sum = ParseUtil.parseInteger(event.getModalId().substring("verification-modal_".length()));

        if (!sum.isPresent()) {
            event.reply("Błąd wewnętrzny. Zgłoś do administracji").setEphemeral(true).queue();
            return;
        }

        ModalMapping sumMapping = event.getValue("verification-modal-sum");

        if (sumMapping == null) {
            event.reply("Błąd wewnętrzny. Zgłoś do administracji").setEphemeral(true).queue();
            return;
        }

        String modalResult = sumMapping.getAsString();

        Optional<Integer> sum2 = ParseUtil.parseInteger(modalResult);

        if (!sum2.isPresent()) {
            event.reply(this.messageConfig.wrongAnswer).setEphemeral(true).queue();
            return;
        }

        if (sum.get().intValue() != sum2.get().intValue()) {
            event.reply(this.messageConfig.wrongAnswer).setEphemeral(true).queue();
            return;
        }

        try {
            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(verificationConfig.roleId)).queue();

            event.reply(this.messageConfig.verifySuccess).setEphemeral(true).queue();
        } catch (Exception e) {
            event.reply("Błąd wewnętrzny. Zgłoś do administracji").setEphemeral(true).queue();

            this.botLogger.log(new BotMessage("Niepowodzenie przy nadawaniu roli użytkownikowi {member}" + e.getMessage()).with(MapBuilder.of("member", event.getMember().getAsMention())));
        }
    }
}