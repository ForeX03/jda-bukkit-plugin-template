package cc.dreamcode.dsmp.discord.jda.embed;

import cc.dreamcode.utilities.StringUtil;
import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
public class DreamEmbed {

    private String title = null;
    private String titleUrl = null;
    private String thumbnailUrl = null;
    private String color = "#ffffff";
    private String author = null;
    private String authorUrl = null;
    private String authorIconUrl = null;
    private List<String> description = new ArrayList<>();
    private String imageUrl = null;
    private String footer = null;
    private String footerIconUrl = null;
    private List<DreamEmbedField> fields = new ArrayList<>();

    public DreamEmbed setFooterIconUrl(String footerIconUrl) {
        this.footerIconUrl = footerIconUrl;
        return this;
    }

    public DreamEmbed addFields(List<DreamEmbedField> fields) {
        this.fields.addAll(fields);
        return this;
    }

    public DreamEmbed addFields(DreamEmbedField... fields) {
        this.fields.addAll(Arrays.asList(fields));
        return this;
    }

    public DreamEmbed addField(DreamEmbedField field) {
        this.fields.add(field);
        return this;
    }

    public DreamEmbed addField(String title, String description) {
        this.fields.add(new DreamEmbedField(title, description));
        return this;
    }

    public DreamEmbed addField(String title, String description, boolean inline) {
        this.fields.add(new DreamEmbedField(title, description, inline));
        return this;
    }

    public DreamEmbed setFooter(String footer) {
        this.footer = footer;
        return this;
    }

    public DreamEmbed setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public DreamEmbed setTitle(String title) {
        this.title = title;
        return this;
    }

    public DreamEmbed setTitleUrl(String titleUrl) {
        this.titleUrl = titleUrl;
        return this;
    }

    public DreamEmbed setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    public DreamEmbed setColor(String color) {
        this.color = color;
        return this;
    }

    public DreamEmbed setAuthor(String author) {
        this.author = author;
        return this;
    }

    public DreamEmbed setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
        return this;
    }

    public DreamEmbed setAuthorIconUrl(String authorIconUrl) {
        this.authorIconUrl = authorIconUrl;
        return this;
    }

    public DreamEmbed addDescription(List<String> description) {
        this.description.addAll(description);
        return this;
    }

    public DreamEmbed addDescription(String description) {
        this.description.add(description);
        return this;
    }

    public MessageEmbed build() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(this.title, this.titleUrl);
        embedBuilder.setColor(Color.decode(this.color));
        embedBuilder.setThumbnail(this.thumbnailUrl);
        embedBuilder.setAuthor(this.author, this.authorUrl, this.authorIconUrl);
        embedBuilder.setDescription(StringUtil.join(description, "\n"));
        embedBuilder.setImage(this.getImageUrl());
        embedBuilder.setFooter(this.footer, this.footerIconUrl);

        this.fields.forEach(field -> embedBuilder.addField(field.getTitle(), field.getDescription(), field.isInline()));

        return embedBuilder.build();
    }
}
