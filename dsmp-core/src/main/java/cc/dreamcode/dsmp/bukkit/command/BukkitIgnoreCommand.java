package cc.dreamcode.dsmp.bukkit.command;

import cc.dreamcode.command.CommandBase;
import cc.dreamcode.command.annotation.*;
import cc.dreamcode.dsmp.core.config.MessageConfig;
import cc.dreamcode.dsmp.core.profile.Profile;
import cc.dreamcode.dsmp.core.profile.ProfileCache;
import cc.dreamcode.notice.NoticeType;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@Command(name = "ignore", aliases = { "ignoruj" })
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BukkitIgnoreCommand implements CommandBase {

    private final MessageConfig messageConfig;
    private final ProfileCache profileCache;

    @Async
    @Completion(arg = "ignorePlayer", value = "@allplayers")
    @Executor
    BukkitNotice ignore(Player player, @Arg String ignorePlayer) {
        Profile profile = this.profileCache.get(player);

        if (profile == null) {
            return BukkitNotice.of(NoticeType.DO_NOT_SEND);
        }

        if (profile.hasIgnored(ignorePlayer)) {
            profile.removeIgnore(ignorePlayer);
            profile.save();

            return this.messageConfig.msgUnignored;
        }

        profile.addIgnore(ignorePlayer);
        profile.save();
        return this.messageConfig.msgIgnored;
    }
}
