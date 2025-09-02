package cc.dreamcode.dsmp.core.whitelist;

import cc.dreamcode.dsmp.core.config.MessageConfig;
import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.dsmp.core.config.subconfig.WhitelistConfig;
import cc.dreamcode.dsmp.core.whitelist.event.WhitelistAddEvent;
import cc.dreamcode.dsmp.core.whitelist.event.WhitelistRemoveEvent;
import cc.dreamcode.dsmp.discord.jda.sender.logger.BotLogger;
import cc.dreamcode.utilities.StringUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.StringColorUtil;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Member;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.List;


@RequiredArgsConstructor(onConstructor_ = @Inject)
public class WhitelistService {

    private final WhitelistStorage whitelistStorage;
    private final Server server;
    private final BotLogger botLogger;
    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;
    private final Tasker tasker;

    public int size() {
        return this.whitelistStorage.whitelist.size();
    }

    public List<String> getWhitelistedPlayers() {
        return this.whitelistStorage.whitelist;
    }

    public boolean isEnabled() {
        return this.whitelistStorage.enabled;
    }

    public void setEnabled(Member member, boolean enabled) {
        this.setEnabled("<@" + member.getId() + ">", enabled);
    }

    public void setEnabled(Player player, boolean enabled) {
        this.setEnabled(player.getName(), enabled);
    }

    public void setEnabled(String executor, boolean enabled) {
        this.whitelistStorage.enabled = enabled;
        this.whitelistStorage.save();

        if (enabled) {
            this.botLogger.log(this.messageConfig.logWhitelistEnabled, MapBuilder.of("member", executor));
            return;
        }
        this.botLogger.log(this.messageConfig.logWhitelistDisabled, MapBuilder.of("member", executor));
    }

    public boolean isWhitelisted(Player player) {
        return this.isWhitelisted(player.getName());
    }

    public boolean isWhitelisted(String name) {
        return this.whitelistStorage.whitelist.contains(name);
    }

    public void addPlayer(Member member, String name) {
        this.addPlayer("<@" + member.getId() + ">", name);
    }

    public void addPlayer(Player player, String name) {
        this.addPlayer(player.getName(), name);
    }

    public void addPlayer(String executor, String name) {
        if (isWhitelisted(name)) {
            return;
        }

        this.whitelistStorage.whitelist.add(name);
        this.whitelistStorage.save();



        this.tasker.newChain()
                .sync(() -> {
                    this.server.getPluginManager().callEvent(new WhitelistAddEvent(name, this.whitelistStorage.whitelist));
                    this.botLogger.log(this.messageConfig.logWhitelistAdd, MapBuilder.of("member", executor, "nick", name));
                })
                .execute();
    }

    public void removePlayer(Member member, String name) {
        this.removePlayer("<@" + member.getId() + ">", name);
    }

    public void removePlayer(Player player, String name) {
        this.removePlayer(player.getName(), name);
    }

    public void removePlayer(String executor, String name) {
        WhitelistConfig whitelistConfig = this.pluginConfig.whitelistConfig;
        if (!isWhitelisted(name)) {
            return;
        }

        this.whitelistStorage.whitelist.remove(name);
        this.whitelistStorage.save();

        this.tasker.newChain()
                .sync(() -> {
                    this.server.getPluginManager().callEvent(new WhitelistRemoveEvent(name, this.whitelistStorage.whitelist));
                    this.botLogger.log(this.messageConfig.logWhitelistRemove, MapBuilder.of("member", executor, "nick", name));
                })
                .execute();

        if (whitelistConfig.kickAfterRemove) {
            return;
        }

        if (!this.whitelistStorage.enabled) {
            return;
        }

        Player removedPlayer = Bukkit.getPlayer(name);

        if (removedPlayer == null) {
            return;
        }

        String reason = StringColorUtil.fixColor(StringUtil.join(whitelistConfig.removedKick, "\n&r"));

        this.tasker.newChain()
                .sync(() -> removedPlayer.kickPlayer(reason))
                .execute();
    }
}
