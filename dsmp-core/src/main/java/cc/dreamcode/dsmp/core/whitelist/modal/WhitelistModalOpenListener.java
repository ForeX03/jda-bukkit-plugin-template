package cc.dreamcode.dsmp.core.whitelist.modal;

import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.dsmp.core.config.subconfig.WhitelistConfig;
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
public class WhitelistModalOpenListener extends ListenerAdapter {

    private final PluginConfig pluginConfig;

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        WhitelistConfig whitelistConfig = this.pluginConfig.whitelistConfig;

        if (!event.getButton().getId().equals("whitelist-open-modal")) {
            return;
        }

        TextInput textInput = TextInput.create("whitelist-modal-nick", whitelistConfig.textInputLabel, TextInputStyle.SHORT)
                .setRequired(true)
                .setRequiredRange(whitelistConfig.minLength, whitelistConfig.maxLength)
                .build();

        Modal modal = Modal.create("whitelist-modal", whitelistConfig.modalTitle)
                .addComponents(ActionRow.of(textInput))
                .build();

        event.replyModal(modal).queue();
    }
}
