package cc.dreamcode.dsmp.core.activity.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import net.dv8tion.jda.api.entities.Activity;

public class ActivitySerializer implements ObjectSerializer<Activity> {
    @Override
    public boolean supports(@NonNull Class<? super Activity> type) {
        return Activity.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull Activity object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("activity-type", object.getType());
        data.add("text", object.getName());

        if (object.getUrl() != null) {
            data.add("url", object.getUrl());
        }
    }

    @Override
    public Activity deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        Activity.ActivityType activityType = data.get("activity-type", Activity.ActivityType.class);
        String text = data.get("text", String.class);

        if (data.containsKey("url")) {
            String url = data.get("url", String.class);

            return Activity.of(activityType, text, url);
        }

        return Activity.of(activityType, text);
    }
}
