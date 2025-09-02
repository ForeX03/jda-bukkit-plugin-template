package cc.dreamcode.dsmp.bukkit.command;

import cc.dreamcode.command.CommandBase;
import cc.dreamcode.command.annotation.*;
import cc.dreamcode.dsmp.core.config.MessageConfig;
import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.dsmp.core.config.subconfig.WhitelistConfig;
import cc.dreamcode.dsmp.core.whitelist.WhitelistService;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.utilities.StringUtil;
import cc.dreamcode.utilities.bukkit.StringColorUtil;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Command(name = "whitelist", aliases = { "wl", "dsmp-wl" })
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BukkitWhitelistCommand implements CommandBase {

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;
    private final WhitelistService whitelistService;

    @Completion(arg = "name", value = "@allplayers")
    @Permission("dreamsmp.whitelist.add")
    @Executor(path = "add", description = "Dodaje gracza na whiteliste")
    BukkitNotice add(Player executor, @Arg String name) {
        if (this.whitelistService.isWhitelisted(name)) {
            return this.messageConfig.wlAlrAdded.with("nick", name);
        }

        this.whitelistService.addPlayer(executor, name);

        return this.messageConfig.wlAdded.with("nick", name);
    }

    @Completion(arg = "name", value = "@wlplayers")
    @Permission("dreamsmp.whitelist.remove")
    @Executor(path = "remove", description = "Usuwa gracza z whitelisty")
    BukkitNotice remove(Player executor, @Arg String name) {
        WhitelistConfig whitelistConfig = this.pluginConfig.whitelistConfig;

        if (!this.whitelistService.isWhitelisted(name)) {
            return this.messageConfig.wlNotAdded.with("nick", name);
        }

        this.whitelistService.removePlayer(executor, name);

        if (!whitelistConfig.kickAfterRemove) {
            return this.messageConfig.wlRemoved.with("nick", name);
        }

        if (!this.whitelistService.isEnabled()) {
            return this.messageConfig.wlRemoved.with("nick", name);
        }

        Player target = Bukkit.getPlayer(name);

        if (target != null) {
            String reason = StringColorUtil.fixColor(StringUtil.join(whitelistConfig.removedKick, "\n&r"));

            target.kickPlayer(reason);
        }

        return  this.messageConfig.wlRemoved.with("nick", name);
    }

    @Permission("dreamsmp.whitelist.on")
    @Executor(path = "on", description = "Aktywuje whiteliste")
    BukkitNotice on(Player executor) {
        if (this.whitelistService.isEnabled()) {
            return this.messageConfig.wlAlrEnabled;
        }

        this.whitelistService.setEnabled(executor, true);

        return this.messageConfig.wlEnabled;
    }

    @Permission("dreamsmp.whitelist.off")
    @Executor(path = "off", description = "Dezaktywuje whiteliste")
    BukkitNotice off(Player executor) {
        if (!this.whitelistService.isEnabled()) {
            return this.messageConfig.wlAlrDisabled;
        }

        this.whitelistService.setEnabled(executor, false);

        return this.messageConfig.wlDisabled;
    }
}
