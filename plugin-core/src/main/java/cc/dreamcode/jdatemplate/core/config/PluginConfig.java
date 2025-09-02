package cc.dreamcode.jdatemplate.core.config;

import cc.dreamcode.jdatemplate.core.config.subconfig.*;
import cc.dreamcode.jdatemplate.discord.jda.embed.DreamEmbed;
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
    public StorageConfig storageConfig = new StorageConfig("jdatemplate");

    @Comment
    @CustomKey("bot")
    public BotConfig botConfig = new BotConfig();

    @Comment
    @CustomKey("embeds")
    public Map<String, DreamEmbed> embedMap = new MapBuilder<String, DreamEmbed>()
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
