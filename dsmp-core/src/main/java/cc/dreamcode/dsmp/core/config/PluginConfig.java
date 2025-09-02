package cc.dreamcode.dsmp.core.config;

import cc.dreamcode.dsmp.core.config.subconfig.*;
import cc.dreamcode.dsmp.discord.jda.embed.DreamEmbed;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.platform.persistence.StorageConfig;
import cc.dreamcode.utilities.builder.ListBuilder;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import eu.okaeri.configs.annotation.Header;

import java.util.Map;

@Configuration(child = "config.yml")
@Header("## DSMP-Core (Main-Config) ##")
public class PluginConfig extends OkaeriConfig {

    @Comment
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    @CustomKey("debug")
    public boolean debug = true;

    @Comment
    @Comment("Ponizej znajduja sie dane do logowania bazy danych:")
    @CustomKey("storage-config")
    public StorageConfig storageConfig = new StorageConfig("dsmp-core");

    @Comment
    @CustomKey("bot")
    public BotConfig botConfig = new BotConfig();

    @Comment
    @CustomKey("chat-bridge")
    public BridgeConfig bridgeConfig = new BridgeConfig();

    @Comment
    @CustomKey("chat")
    public ChatConfig chatConfig = new ChatConfig();

    @Comment
    @CustomKey("whitelist")
    public WhitelistConfig whitelistConfig = new WhitelistConfig();

    @Comment
    @CustomKey("msg")
    public DirectMessageConfig directMessageConfig = new DirectMessageConfig();

    @Comment
    @CustomKey("bot-activity")
    public ActivityConfig activityConfig = new ActivityConfig();

    @Comment
    @CustomKey("verification")
    public VerificationConfig verificationConfig = new VerificationConfig();

    @Comment
    @CustomKey("portal")
    public PortalConfig portalConfig = new PortalConfig();

    @Comment
    @CustomKey("world-border")
    public WorldBorderConfig worldBorderConfig = new WorldBorderConfig();

    @Comment
    @CustomKey("channel-stats")
    public StatsConfig statsConfig = new StatsConfig();

    @Comment
    @Comment("Embedy możliwe do wysłania przez komende /dsmp send-embed na serwerze discord")
    @CustomKey("embeds")
    public Map<String, DreamEmbed> embedMap = new MapBuilder<String, DreamEmbed>()
            .put("season-coming", new DreamEmbed()
                    .setColor("#7e4abb")
                    .addDescription(ListBuilder.of(
                            "## DreamSMP - II EDYCJA NADCHODZI! 🌟",
                            " ",
                            "### 🛠️ Ostatnie poprawki w toku!",
                            "🔧 **Prace nad serwerem zbliżają się ku końcowi!**",
                            " ",
                            "### ⏰ Kiedy startujemy?",
                            "📅 Już niedługo podamy dokładną datę – **obserwuj nas, żeby nic Ci nie umknęło!**",
                            " ",
                            "### 🆕 Co nowego czeka na Ciebie?",
                            "🔥 Przekonasz się wkrótce – **będzie jazda!**",
                            " ",
                            "### 🚀 ZAPROŚ ZNAJOMYCH I SZYKUJ SIĘ NA START!",
                            "Nie przegap tego – **widzimy się już wkrótce** 💥"
                    ))
                    .setImageUrl("https://i.imgur.com/temrMHs.png"))
            .put("test-embed", new DreamEmbed()
                    .setColor("#ffffff")
                    .setTitle("title")
                    .setTitleUrl("https://www.youtube.com/@Keymilo")
                    .setAuthor("ForeX03")
                    .setAuthorIconUrl("https://cdn.discordapp.com/avatars/578648970163912715/3826f93c4393b6f7dbe7625d572947d7.webp")
                    .setAuthorUrl("https://www.youtube.com/@ForeX032")
                    .setImageUrl("https://i.imgur.com/temrMHs.png")
                    .addDescription(ListBuilder.of("description"))
                    .addField("Pole 1", "pole 1", true)
                    .addField("Pole 2", "pole 2", true)
                    .addField("Pole 3", "pole 3")
                    .setFooter("DreamSMP")
                    .setFooterIconUrl("https://cdn.discordapp.com/icons/1360952302801784924/de732480cd4bd30f536773ba8c710f21.webp?size=80&quality=lossless"))
            .build();
}
