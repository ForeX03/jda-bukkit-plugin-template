package cc.dreamcode.jdatemplate.discord.jda.embed.serializer;

import cc.dreamcode.jdatemplate.discord.jda.embed.DreamEmbed;
import cc.dreamcode.jdatemplate.discord.jda.embed.DreamEmbedField;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;

public class DreamEmbedSerializer implements ObjectSerializer<DreamEmbed> {
    @Override
    public boolean supports(@NonNull Class<? super DreamEmbed> type) {
        return DreamEmbed.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull DreamEmbed object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {

        if (object.getTitle() != null) {
            data.add("title", object.getTitle());
        }

        if (object.getTitleUrl() != null) {
            data.add("title-url", object.getTitleUrl());
        }

        if (object.getAuthor() != null) {
            data.add("author", object.getAuthor());
        }

        if (object.getAuthorUrl() != null) {
            data.add("author-url", object.getAuthorUrl());
        }

        if (object.getAuthorIconUrl() != null) {
            data.add("author-icon-url", object.getAuthorIconUrl());
        }

        if (object.getColor() != null) {
            data.add("color", object.getColor());
        }

        if (object.getThumbnailUrl() != null) {
            data.add("thumbnail-url", object.getThumbnailUrl());
        }

        if (!object.getDescription().isEmpty()) {
            data.add("description", object.getDescription());
        }

        if (object.getImageUrl() != null) {
            data.add("image-url", object.getImageUrl());
        }

        if (object.getFooter() != null) {
            data.add("footer", object.getFooter());
        }

        if (object.getFooterIconUrl() != null) {
            data.add("footer-icon-url", object.getFooterIconUrl());
        }

        if (!object.getFields().isEmpty()) {
            data.add("fields", object.getFields());
        }
    }

    @Override
    public DreamEmbed deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        DreamEmbed dreamEmbed = new DreamEmbed();

        if (data.containsKey("title")) {
            dreamEmbed.setTitle(data.get("title", String.class));
        }

        if (data.containsKey("title-url")) {
            dreamEmbed.setTitleUrl(data.get("title-url", String.class));
        }

        if (data.containsKey("author")) {
            dreamEmbed.setAuthor(data.get("author", String.class));
        }

        if (data.containsKey("author-url")) {
            dreamEmbed.setAuthorUrl(data.get("author-url", String.class));
        }

        if (data.containsKey("author-icon-url")) {
            dreamEmbed.setAuthorIconUrl(data.get("author-icon-url", String.class));
        }

        if (data.containsKey("color")) {
            dreamEmbed.setColor(data.get("color", String.class));
        }

        if (data.containsKey("thumbnail-url")) {
            dreamEmbed.setThumbnailUrl(data.get("thumbnail-url", String.class));
        }

        if (data.containsKey("description")) {
            dreamEmbed.addDescription(data.getAsList("description", String.class));
        }

        if (data.containsKey("image-url")) {
            dreamEmbed.setImageUrl(data.get("image-url", String.class));
        }

        if (data.containsKey("footer")) {
            dreamEmbed.setFooter(data.get("footer", String.class));
        }

        if (data.containsKey("footer-icon-url")) {
            dreamEmbed.setFooterIconUrl(data.get("footer-icon-url", String.class));
        }

        if (data.containsKey("fields")) {
            dreamEmbed.addFields(data.getAsList("fields", DreamEmbedField.class));
        }

        return dreamEmbed;
    }
}
