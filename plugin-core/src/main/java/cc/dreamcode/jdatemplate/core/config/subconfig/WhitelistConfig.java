package cc.dreamcode.jdatemplate.core.config.subconfig;

import cc.dreamcode.jdatemplate.discord.jda.embed.DreamEmbed;
import cc.dreamcode.utilities.builder.ListBuilder;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;

import java.util.List;

public class WhitelistConfig extends OkaeriConfig {

    @CustomKey("enabled")
    public boolean enabled = false;

    @CustomKey("role-id")
    public String roleId = "";

    @CustomKey("rename-users")
    public boolean renameUsers = true;

    @CustomKey("kick-message")
    public List<String> kick = new ListBuilder<String>()
            .add("&d&lDreamSMP")
            .add("&cTen nick nie znajduje się na &lbiałej liście&r&c!")
            .add(" ")
            .add("&fDołącz na naszego discorda, aby dodać swój nick na białą listę")
            .build();

    @CustomKey("embed")
    public DreamEmbed embed = new DreamEmbed()
            .setColor("#7e4abb")
            .setThumbnailUrl("https://cdn.discordapp.com/icons/1360952302801784924/de732480cd4bd30f536773ba8c710f21.webp?size=80&quality=lossless")
            .addDescription(ListBuilder.of(
                    "# Witaj na DreamSMP! \uD83C\uDF89",
                    " ",
                    "**Super, że jesteś z nami!**",
                    "Żeby dołączyć do gry, **kliknij przycisk poniżej** i wpisz swój nick – nasz bot zajmie się resztą! 🧙‍♂️",
                    " ",
                    "Chcesz zaprosić znajomych? Wyślij im ten link: dsc.gg/dream-smp, aby również mogli dołączyć do naszego Discorda i **wspólnie z Tobą** wziąć udział w rozgrywce.",
                    " ",
                    "Pamiętaj o przestrzeganiu **zasad**, - znajdziesz je na kanale <#1360959625570160718> Nieprzestrzeganie zasad może skutkować **banem i wykluczeniem z gry**.",
                    " ",
                    "**Baw się świetnie, poznawaj nowych znajomych i zbuduj coś niesamowitego!** ✨"
            ));

    @CustomKey("button-label")
    public String buttonLabel = "Kliknij przycisk, aby dodać swój nick na białą listę!";

    @CustomKey("modal-title")
    public String modalTitle = "DreamSMP - Whitelista";

    @CustomKey("text-input-label")
    public String textInputLabel = "Wpisz swój nick:";

    @CustomKey("nick-max-length")
    public int maxLength = 16;

    @CustomKey("nick-min-length")
    public int minLength = 3;

    @Comment("Czy wyrzucic graczy nie dodanych do bialej listy w trakcie jej wlaczenia?")
    @CustomKey("kick-on-enable")
    public boolean kickOnEnable = true;

    @CustomKey("enable-kick-message")
    public List<String> enabledKick = new ListBuilder<String>()
            .add("&d&lDreamSMP")
            .add("&cAdministrator aktywował &lbiałą listę")
            .add("&cTen nick się na niej nie znajduje!")
            .add(" ")
            .add("&fDołącz na naszego discorda, aby dodać swój nick na białą listę")
            .build();

    @CustomKey("kick-on-remove")
    public boolean kickAfterRemove = true;

    @CustomKey("remove-kick-message")
    public List<String> removedKick = new ListBuilder<String>()
            .add("&d&lDreamSMP")
            .add("&cAdministrator usunął cię z &lbiałej listy")
            .build();
}
