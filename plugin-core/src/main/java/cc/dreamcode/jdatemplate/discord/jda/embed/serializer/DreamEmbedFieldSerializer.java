package cc.dreamcode.jdatemplate.discord.jda.embed.serializer;

import cc.dreamcode.jdatemplate.discord.jda.embed.DreamEmbedField;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;

public class DreamEmbedFieldSerializer implements ObjectSerializer<DreamEmbedField> {
    @Override
    public boolean supports(@NonNull Class<? super DreamEmbedField> type) {
        return DreamEmbedField.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull DreamEmbedField object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("title", object.getTitle());
        data.add("description", object.getDescription());
        if (object.isInline()) {
            data.add("inline", true);
        }
    }

    @Override
    public DreamEmbedField deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        String title = data.get("title", String.class);
        String description = data.get("description", String.class);
        Boolean inline = data.containsKey("inline") ? data.get("inline", Boolean.class) : false;

        return new DreamEmbedField(title, description, inline);
    }
}
