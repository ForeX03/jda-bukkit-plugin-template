package cc.dreamcode.dsmp.core.channelstats;

import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.dsmp.core.config.subconfig.StatsConfig;
import cc.dreamcode.dsmp.core.whitelist.WhitelistService;
import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import cc.dreamcode.utilities.StringUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import org.bukkit.Server;

import java.util.Map;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@Scheduler(delay = 0L, interval = 60 * 20L)
public class StatsScheduler implements Runnable {

    private final PluginConfig pluginConfig;
    private final WhitelistService whitelistService;
    private final Server server;
    private final JDA jda;

    @Override
    public void run() {
        StatsConfig statsConfig = this.pluginConfig.statsConfig;

        Map<String, Object> replaceMap = MapBuilder.of(
                "whitelisted", this.whitelistService.size(),
                "mc_online", this.server.getOnlinePlayers().size()
        );

        statsConfig.channelMap.forEach((channelId, name) -> {
            VoiceChannel statChannel = this.jda.getVoiceChannelById(channelId);

            if (statChannel == null) {
                return;
            }

            statChannel.getManager().setName(StringUtil.replace(name, replaceMap)).queue();
        });

    }
}
