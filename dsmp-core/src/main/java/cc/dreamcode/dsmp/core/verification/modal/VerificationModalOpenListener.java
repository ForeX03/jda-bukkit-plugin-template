package cc.dreamcode.dsmp.core.verification.modal;

import cc.dreamcode.dsmp.core.config.MessageConfig;
import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.dsmp.core.config.subconfig.VerificationConfig;
import cc.dreamcode.utilities.RandomUtil;
import cc.dreamcode.utilities.StringUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class VerificationModalOpenListener extends ListenerAdapter {

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        VerificationConfig verificationConfig = this.pluginConfig.verificationConfig;

        if (!event.getButton().getId().equals("verification-open-modal")) {
            return;
        }

        if (event.getMember().getRoles().stream().anyMatch(role -> role.getId().equals(verificationConfig.roleId))) {
            event.reply(this.messageConfig.verifiedYet).setEphemeral(true).queue();
            return;
        }

        int i1 = RandomUtil.nextInteger(verificationConfig.minNumber, verificationConfig.maxNumber);

        int i2 = RandomUtil.nextInteger(verificationConfig.minNumber, verificationConfig.maxNumber);;

        TextInput textInput = TextInput.create("verification-modal-sum", StringUtil.replace(verificationConfig.textInputLabel, MapBuilder.of("num1", i1, "num2", i2)), TextInputStyle.SHORT)
                .setRequired(true)
                .build();

        int sum = i1+i2;

        Modal modal = Modal.create("verification-modal_"+sum, verificationConfig.modalTitle)
                .addComponents(ActionRow.of(textInput))
                .build();

        event.replyModal(modal).queue();
    }
}
