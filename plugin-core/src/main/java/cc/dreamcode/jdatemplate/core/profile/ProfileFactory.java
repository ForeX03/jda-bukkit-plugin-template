package cc.dreamcode.jdatemplate.core.profile;

import cc.dreamcode.platform.DreamLogger;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.RequiredArgsConstructor;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;

import java.util.function.Consumer;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ProfileFactory {
    private final ProfileCache profileCache;
    private final Tasker tasker;
    private final DreamLogger dreamLogger;
    private final ProfileRepository profileRepository;
    private final Server server;

    public void loadAll() {
        this.profileRepository.findAll().forEach(this.profileCache::add);
    }

    public void loadOnline() {
        this.server.getOnlinePlayers().forEach(this::createProfile);
    }

    public void createProfile(HumanEntity humanEntity) {
        this.createProfile(humanEntity, null);
    }

    public void createProfile(HumanEntity humanEntity, Consumer<Profile> consumer) {
        if (this.profileCache.contains(humanEntity.getUniqueId())) {
            Profile profile = this.profileCache.get(humanEntity.getUniqueId());

            if (consumer != null) {
                consumer.accept(profile);
            }
            profile.save();
            return;
        }

        this.tasker.newChain()
                .supplyAsync(() -> this.profileRepository.findOrCreateByPath(humanEntity.getUniqueId()))
                .acceptSync(profile -> {

                    profile.setName(humanEntity.getName());

                    if (consumer != null) {
                        consumer.accept(profile);
                    }
                })
                .acceptAsync(Profile::save)
                .abortIfException(e -> this.dreamLogger.error("Cannot save profile to database", e))
                .acceptAsync(this.profileCache::add)
                .execute();
    }
}
