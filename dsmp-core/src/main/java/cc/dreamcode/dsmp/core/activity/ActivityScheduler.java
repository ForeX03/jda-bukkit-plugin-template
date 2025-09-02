package cc.dreamcode.dsmp.core.activity;

import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.dsmp.core.whitelist.WhitelistService;
import cc.dreamcode.platform.bukkit.DreamBukkitPlatform;
import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import cc.dreamcode.utilities.StringUtil;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Server;

import java.util.HashMap;
import java.util.Map;

@Scheduler(delay = 0L, interval = 200L)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ActivityScheduler implements Runnable {

    private final PluginConfig pluginConfig;
    private final Server server;
    private final JDA jda;
    private final DreamBukkitPlatform platform;

    private int iteration = 0;

    @Override
    public void run() {
        if (iteration == this.pluginConfig.activityConfig.activityList.size()) {
            this.iteration = 0;
        }

        Map<String, Object> replaceMap = new HashMap<>();
        replaceMap.put("online", this.server.getOnlinePlayers().size());

        this.platform.getInject(WhitelistService.class).ifPresent(service -> replaceMap.put("whitelist", service.size()));

        Activity activity = this.pluginConfig.activityConfig.activityList.get(iteration);
        Activity newActivity = Activity.of(activity.getType(), StringUtil.replace(activity.getName(), replaceMap), activity.getUrl());

        this.jda.getPresence().setActivity(newActivity);

        this.iteration++;
    }
}
