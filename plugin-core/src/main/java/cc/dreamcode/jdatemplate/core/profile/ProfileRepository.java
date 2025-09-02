package cc.dreamcode.jdatemplate.core.profile;

import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;

import java.util.UUID;

@DocumentCollection(path = "profiles", keyLength = 36)
public interface ProfileRepository extends DocumentRepository<UUID, Profile> {
}
