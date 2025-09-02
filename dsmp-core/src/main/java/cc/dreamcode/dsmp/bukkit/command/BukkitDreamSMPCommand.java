package cc.dreamcode.dsmp.bukkit.command;

import cc.dreamcode.command.CommandBase;
import cc.dreamcode.command.annotation.*;
import cc.dreamcode.dsmp.core.config.MessageConfig;
import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.utilities.TimeUtil;
import eu.okaeri.configs.exception.OkaeriException;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;

@Command(name = "dreamsmp", aliases = {"dsmp"})
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BukkitDreamSMPCommand implements CommandBase {

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;

    @Async
    @Permission("dreamsmp.reload")
    @Executor(path = "reload", description = "Przeladowuje konfiguracje.")
    BukkitNotice reload() {
        final long time = System.currentTimeMillis();

        try {
            this.messageConfig.load();
            this.pluginConfig.load();

            return this.messageConfig.reloaded
                    .with("time", TimeUtil.format(System.currentTimeMillis() - time));
        }
        catch (NullPointerException | OkaeriException e) {

            return this.messageConfig.reloadError
                    .with("error", e.getMessage());
        }
    }

    @Async
    @Permission("dreamsmp.nether")
    @Completion(arg = "state", value = { "on", "off" })
    @Executor(path = "nether", description = "Włącza/wyłącza nether")
    BukkitNotice toggleNether(@Arg String state) {
        if (state.equalsIgnoreCase("on")) {
            if (this.pluginConfig.portalConfig.netherEnabled) {
                return this.messageConfig.netherAlreadyEnabled;
            }

            this.pluginConfig.portalConfig.netherEnabled = true;
            this.pluginConfig.save();

            this.messageConfig.netherEnabledBroadcast.sendAll();

            return this.messageConfig.netherEnabled;
        }

        if (state.equalsIgnoreCase("off")) {
            if (!this.pluginConfig.portalConfig.netherEnabled) {
                return this.messageConfig.netherAlreadyDisabled;
            }

            this.pluginConfig.portalConfig.netherEnabled = false;
            this.pluginConfig.save();

            this.messageConfig.netherDisabledBroadcast.sendAll();

            return this.messageConfig.netherDisabled;
        }

        return this.messageConfig.usage.with("label", "/dsmp nether on/off");
    }

    @Async
    @Permission("dreamsmp.end")
    @Completion(arg = "state", value = { "on", "off" })
    @Executor(path = "end", description = "Włącza/wyłącza nether")
    BukkitNotice toggleEnd(@Arg String state) {
        if (state.equalsIgnoreCase("on")) {
            if (this.pluginConfig.portalConfig.endEnabled) {
                return this.messageConfig.endAlreadyEnabled;
            }

            this.pluginConfig.portalConfig.endEnabled = true;
            this.pluginConfig.save();

            this.messageConfig.endEnabledBroadcast.sendAll();

            return this.messageConfig.endEnabled;
        }

        if (state.equalsIgnoreCase("off")) {
            if (!this.pluginConfig.portalConfig.endEnabled) {
                return this.messageConfig.endAlreadyDisabled;
            }

            this.pluginConfig.portalConfig.endEnabled = false;
            this.pluginConfig.save();

            this.messageConfig.endDisabledBroadcast.sendAll();

            return this.messageConfig.endDisabled;
        }

        return this.messageConfig.usage.with("label", "/dsmp end on/off");
    }

}
