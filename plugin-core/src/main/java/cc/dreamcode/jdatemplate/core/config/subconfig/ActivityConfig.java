package cc.dreamcode.jdatemplate.core.config.subconfig;

import cc.dreamcode.utilities.builder.ListBuilder;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import net.dv8tion.jda.api.entities.Activity;

import java.util.List;

public class ActivityConfig extends OkaeriConfig {

    @CustomKey("enabled")
    public boolean enabled = true;

    @Comment("Dostępne opcje: COMPETING, PLAYING, WATCHING, LISTENING, CUSTOM_STATUS, STREAMING")
    @CustomKey("list")
    public List<Activity> activityList = ListBuilder.of(
            Activity.of(Activity.ActivityType.WATCHING, "DreamSMP | Online: {online} | WL: {whitelist}"),
            Activity.of(Activity.ActivityType.WATCHING, "DreamSMP -> Zapraszamy do wspólnej gry!")
    );
}
