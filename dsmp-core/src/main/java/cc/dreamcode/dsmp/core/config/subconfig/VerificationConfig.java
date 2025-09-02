package cc.dreamcode.dsmp.core.config.subconfig;

import cc.dreamcode.dsmp.discord.jda.embed.DreamEmbed;
import cc.dreamcode.utilities.builder.ListBuilder;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.CustomKey;

public class VerificationConfig extends OkaeriConfig {

    @CustomKey("min-number")
    public int minNumber = 1;

    @CustomKey("max-number")
    public int maxNumber = 20;

    @CustomKey("role-id")
    public String roleId = "";

    @CustomKey("embed")
    public DreamEmbed embed = new DreamEmbed()
            .setColor("#7e4abb")
            .setTitle("DreamSMP - Weryfikacja")
            .addDescription(ListBuilder.of("> Kliknij przycisk poniżej, aby się zweryfikować!"))
            .setImageUrl("https://i.imgur.com/temrMHs.png");

    @CustomKey("button-label")
    public String buttonLabel = "Kliknij, aby rozpocząć weryfikację";

    @CustomKey("modal-title")
    public String modalTitle = "DreamSMP - Weryfikacja";

    @CustomKey("text-input-label")
    public String textInputLabel = "Wpisz wynik działania: {num1} + {num2}";
}
