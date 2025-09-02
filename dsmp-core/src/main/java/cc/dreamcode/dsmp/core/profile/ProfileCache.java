package cc.dreamcode.dsmp.core.profile;

import lombok.NonNull;
import org.bukkit.entity.HumanEntity;

import java.util.HashMap;
import java.util.Map;

public class ProfileCache {

    private final Map<String, Profile> profileMap = new HashMap<>();

    public void add(@NonNull Profile profile) {
        this.profileMap.put(profile.getName(), profile);
    }

    public void remove(@NonNull Profile profile) {
        this.profileMap.remove(profile.getName());
    }

    public Profile get(@NonNull HumanEntity humanEntity) {
        return get(humanEntity.getName());
    }

    public Profile get(String name) {
        return this.profileMap.get(name);
    }

    public boolean contains(String name) {
        return this.profileMap.containsKey(name);
    }
}
