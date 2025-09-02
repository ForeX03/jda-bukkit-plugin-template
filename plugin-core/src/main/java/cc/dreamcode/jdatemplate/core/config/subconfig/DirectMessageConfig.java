package cc.dreamcode.jdatemplate.core.config.subconfig;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.CustomKey;

public class DirectMessageConfig extends OkaeriConfig {

    @CustomKey("format")
    public String msgFormat = "&8[&a{sender} &7-> &a{target}&8] &7{message}";

    @CustomKey("player-placeholder")
    public String msgPlayerPlaceholder = "&2Ty";
}
