package cc.dreamcode.jdatemplate.core.config.subconfig;

import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;

import java.util.Map;

public class BridgeConfig extends OkaeriConfig {
    public boolean enabled = false;

    public DiscordOptions discordOptions = new DiscordOptions();

    public MinecraftOptions minecraftOptions = new MinecraftOptions();

    public class DiscordOptions extends OkaeriConfig {
        @CustomKey("channel-id")
        public String chatChannelId = "";

        @CustomKey("webhook-url")
        public String webhookUrl = "";

        @CustomKey("avatar-url")
        public String avatarUrl = "https://minotar.net/helm/{player}/100.png";

        @CustomKey("webhook-name")
        public String name = "{player}";

        @Comment("Format wiadomości wysłanej na discorda")
        @CustomKey("format")
        public String format = "{message}";

        @Comment
        @CustomKey("replace-map")
        public Map<String, String> replaceMap = MapBuilder.of("#", "", "@", "");

    }

    public class MinecraftOptions extends OkaeriConfig {
        @Comment("{reply} zostanie wstawione tylko jesli jest to odpowiedz na wiadomosc")
        @Comment("{format} jest to format chatu w configu ponizej")
        @Comment("inne placeholdery -> player, message")
        @CustomKey("format")
        public String format = "{reply}&9[Discord] {format}";

        @Comment("Wartosc ponizej zostanie wstawiona jako placeholder {reply} jesli wiadomosc jest odpowiedzia")
        @CustomKey("reply")
        public String reply = "&d&n[Odp. dla {player}]&r ";

        @Comment("Tekst, pokazujacy sie jesli najedzie sie kursorem na wiadomosc na chacie")
        @CustomKey("reply-hover")
        public String replyHover = "{timestamp} {player} {message}";

        @Comment
        @CustomKey("replace-map")
        public Map<String, String> replaceMap = new MapBuilder<String, String>()
                .put("&" , "")
                .put("§", "")
                .build();

    }
}
