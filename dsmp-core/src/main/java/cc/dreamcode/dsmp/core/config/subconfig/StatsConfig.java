package cc.dreamcode.dsmp.core.config.subconfig;

import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;

import java.util.Map;

public class StatsConfig extends OkaeriConfig {

    @CustomKey("enabled")
    public boolean enabled = false;

    @Comment("format: id_kanału: \"wyswietlany tekst\"")
    @Comment("dostepne placeholdery: {mc_online}, {whitelisted}")
    @CustomKey("channels")
    public Map<String, String> channelMap = MapBuilder.of("0000000000000000000", "Whitelist: {whitelisted}", "0000000000000000001", "Online: {mc_online}");
}
