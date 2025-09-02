package cc.dreamcode.jdatemplate.core.profile;

import com.google.common.collect.Maps;
import lombok.NonNull;
import org.bukkit.entity.HumanEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileCache {

    private final Map<UUID, Profile> profileMap = Maps.newConcurrentMap();

    private final Map<String, UUID> nameToUuid = Maps.newConcurrentMap();

    public void add(@NonNull Profile profile) {
        this.profileMap.put(profile.getUniqueId(), profile);
        this.nameToUuid.put(profile.getName().toLowerCase(), profile.getUniqueId());
    }

    public void remove(@NonNull Profile profile) {
        this.profileMap.remove(profile.getUniqueId());
    }

    public Profile get(@NonNull HumanEntity humanEntity) {
        return get(humanEntity.getUniqueId());
    }

    public Profile get(@NonNull UUID uuid) {
        return this.profileMap.get(uuid);
    }

    public Profile get(@NonNull String name) {
        UUID uuid = this.nameToUuid.get(name.toLowerCase());

        if (uuid == null) {
            return null;
        }

        return this.profileMap.get(uuid);
    }

    public boolean contains(UUID uuid) {
        return this.profileMap.containsKey(uuid);
    }

    public boolean contains(String name) {
        return this.nameToUuid.containsKey(name);
    }
}
