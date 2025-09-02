package cc.dreamcode.dsmp.bukkit.command;

import cc.dreamcode.command.CommandBase;
import cc.dreamcode.command.annotation.Async;
import cc.dreamcode.command.annotation.Command;
import cc.dreamcode.command.annotation.Executor;
import cc.dreamcode.dsmp.core.config.MessageConfig;
import cc.dreamcode.dsmp.core.profile.Profile;
import cc.dreamcode.dsmp.core.profile.ProfileCache;
import cc.dreamcode.notice.NoticeType;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@Command(name = "msgtoggle")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BukkitMsgToggleCommand implements CommandBase {

    private final MessageConfig messageConfig;
    private final ProfileCache profileCache;

    @Async
    @Executor()
    BukkitNotice toggle(Player player) {
        Profile profile = this.profileCache.get(player);

        if (profile == null) {
            return BukkitNotice.of(NoticeType.DO_NOT_SEND);
        }

        if (profile.isDmEnabled()) {
            profile.setDmEnabled(false);
            profile.save();

            return this.messageConfig.msgDisabled;
        }

        profile.setDmEnabled(true);
        profile.save();

        return this.messageConfig.msgEnabled;
    }
}
