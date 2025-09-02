package cc.dreamcode.dsmp.core.profile;

import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;

@DocumentCollection(path = "profiles", keyLength = 36)
public interface ProfileRepository extends DocumentRepository<String, Profile> {
}
