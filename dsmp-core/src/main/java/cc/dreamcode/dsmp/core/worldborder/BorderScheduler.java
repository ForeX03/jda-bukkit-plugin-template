package cc.dreamcode.dsmp.core.worldborder;

import cc.dreamcode.dsmp.core.config.MessageConfig;
import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.dsmp.core.config.subconfig.WorldBorderConfig;
import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import cc.dreamcode.utilities.DateUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.Server;
import org.bukkit.World;

import java.time.Instant;
import java.time.LocalTime;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@Scheduler(delay = 0L, interval = 60L * 60L * 20L)
public class BorderScheduler implements Runnable {

    private final PluginConfig pluginConfig;

    private final MessageConfig messageConfig;

    private final Server server;

    @Override
    public void run() {
        WorldBorderConfig worldBorderConfig = this.pluginConfig.worldBorderConfig;
        LocalTime localTime = LocalTime.now();

        if (worldBorderConfig.hour != localTime.getHour()) {
            return;
        }

        String dateString = DateUtil.formatOnlyDate(Instant.now());

        if (worldBorderConfig.lastChange.equals(dateString)) {
            return;
        }

        worldBorderConfig.lastChange = dateString;

        World world = this.server.getWorld("world");

        if (world == null) {
            return;
        }

        double newSize = worldBorderConfig.size + ((double) worldBorderConfig.growth / 2);

        worldBorderConfig.size = (int) newSize;

        this.pluginConfig.worldBorderConfig = worldBorderConfig;

        this.pluginConfig.save();

        world.getWorldBorder().setCenter(worldBorderConfig.centerX, worldBorderConfig.centerZ);
        world.getWorldBorder().setSize(newSize);

        this.messageConfig.borderBecomeGrower.sendAll(MapBuilder.of("grow", worldBorderConfig.growth / 2, "size", newSize * 2));
    }
}
