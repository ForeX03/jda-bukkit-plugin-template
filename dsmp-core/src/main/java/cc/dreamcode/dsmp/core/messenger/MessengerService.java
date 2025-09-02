package cc.dreamcode.dsmp.core.messenger;

import cc.dreamcode.dsmp.core.config.MessageConfig;
import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.dsmp.core.config.subconfig.DirectMessageConfig;
import cc.dreamcode.dsmp.core.profile.Profile;
import cc.dreamcode.dsmp.core.profile.ProfileCache;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MessengerService {

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;
    private final MessengerCache messengerCache;
    private final ProfileCache profileCache;

    public void sendDirect(Player sender, Player target, String message) {
        DirectMessageConfig directMessageConfig = this.pluginConfig.directMessageConfig;

        if (target == null) {
            this.messageConfig.playerNotFound.send(sender);
            return;
        }

        if (sender == target) {
            this.messageConfig.cannotTalkToYourself.send(sender);
            return;
        }

        Profile targetProfile = this.profileCache.get(target);

        if (targetProfile != null && !targetProfile.isDmEnabled()) {
            this.messageConfig.msgTargetDmOff.send(sender);
            return;
        }

        if (targetProfile != null && targetProfile.hasIgnored(sender.getName())) {
            this.messageConfig.msgTargetIgnored.send(sender);
            return;
        }

        this.messengerCache.setPlayerReply(sender, target);

        BukkitNotice msgNotice = BukkitNotice.chat(directMessageConfig.msgFormat);
        String playerFormat = directMessageConfig.msgPlayerPlaceholder;

        msgNotice.send(target, MapBuilder.of("sender", sender.getName(), "target", playerFormat, "message", message));
        msgNotice.send(sender, MapBuilder.of("sender", playerFormat, "target", target.getName(), "message", message));
    }

    public void sendReply(Player sender, String message) {
        DirectMessageConfig directMessageConfig = this.pluginConfig.directMessageConfig;

        if (!this.messengerCache.hasReply(sender)) {
            this.messageConfig.msgTargetNotFound.send(sender);
            return;
        }

        Player target = this.messengerCache.getReplyPlayer(sender);

        Profile targetProfile = this.profileCache.get(target);

        if (targetProfile != null && !targetProfile.isDmEnabled()) {
            this.messageConfig.msgTargetDmOff.send(sender);
            return;
        }

        if (targetProfile != null && targetProfile.hasIgnored(sender.getName())) {
            this.messageConfig.msgTargetIgnored.send(sender);
            return;
        }

        this.messengerCache.setPlayerReply(sender, target);

        BukkitNotice msgNotice = BukkitNotice.chat(directMessageConfig.msgFormat);
        String playerFormat = directMessageConfig.msgPlayerPlaceholder;

        msgNotice.send(target, MapBuilder.of("sender", sender.getName(), "target", playerFormat, "message", message));
        msgNotice.send(sender, MapBuilder.of("sender", playerFormat, "target", target.getName(), "message", message));
    }
}
