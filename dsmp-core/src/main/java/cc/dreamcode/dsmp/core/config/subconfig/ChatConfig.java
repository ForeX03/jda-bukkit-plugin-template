package cc.dreamcode.dsmp.core.config.subconfig;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;

public class ChatConfig extends OkaeriConfig {

    @Comment("Format wiadomosci na chacie.")
    @CustomKey("format")
    public String chatFormat = "{player} &7> &f{message}";
}
