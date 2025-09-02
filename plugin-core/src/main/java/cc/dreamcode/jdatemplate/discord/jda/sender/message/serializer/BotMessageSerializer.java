package cc.dreamcode.jdatemplate.discord.jda.sender.message.serializer;

import cc.dreamcode.jdatemplate.discord.jda.sender.message.BotMessage;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;

import java.util.concurrent.TimeUnit;

public class BotMessageSerializer implements ObjectSerializer<BotMessage> {

    @Override
    public boolean supports(@NonNull Class<? super BotMessage> type) {
        return BotMessage.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull BotMessage object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("enabled", object.isEnabled());

        if (!object.isEnabled()) {
            return;
        }

        data.add("text", object.getText());

        if (object.getDeleteDelay() > 0) {
            data.add("delete-delay", object.getDeleteDelay());
            data.add("delete-unit", object.getDeleteTimeUnit());
        }
    }

    @Override
    public BotMessage deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        boolean enabled = data.get("enabled", Boolean.class);
        String text = "";

        if (enabled) {
            text = data.get("text", String.class);
        }

        if (!data.containsKey("delete-delay")) {
            return new BotMessage(enabled, text);
        }

        long deleteDelay = data.get("delete-delay", Long.class);
        TimeUnit deleteTimeUnit = data.containsKey("delete-unit") ? data.get("delete-unit", TimeUnit.class) : TimeUnit.SECONDS;

        return new BotMessage(enabled, text, deleteDelay, deleteTimeUnit);
    }
}
