package cc.dreamcode.jdatemplate.core.config.subconfig;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.CustomKey;

public class BotConfig extends OkaeriConfig {
    @CustomKey("token")
    public String botToken = "";

    @CustomKey("log-channel-id")
    public String logChannelId = "";
}
