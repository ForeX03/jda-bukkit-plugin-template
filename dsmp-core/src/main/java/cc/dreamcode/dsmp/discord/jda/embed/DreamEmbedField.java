package cc.dreamcode.dsmp.discord.jda.embed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class DreamEmbedField {
    private final String title;
    private final String description;
    private boolean inline = false;
}
