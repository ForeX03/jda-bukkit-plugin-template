package cc.dreamcode.dsmp.core.profile;

import cc.dreamcode.platform.DreamLogger;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ProfileFactory {
    private final ProfileCache profileCache;
    private final Tasker tasker;
    private final DreamLogger dreamLogger;
    private final ProfileRepository profileRepository;

    public void loadAll() {
        this.profileRepository.findAll().forEach(this.profileCache::add);
    }

    public void createProfile(String name) {
        this.createProfile(name, null);
    }

    public void createProfile(String name, Consumer<Profile> consumer) {
        if (this.profileCache.contains(name)) {
            Profile profile = this.profileCache.get(name);

            if (consumer != null) {
                consumer.accept(profile);
            }
            profile.save();
            return;
        }

        this.tasker.newChain()
                .supplyAsync(() -> this.profileRepository.findOrCreateByPath(name))
                .acceptSync(profile -> {
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
