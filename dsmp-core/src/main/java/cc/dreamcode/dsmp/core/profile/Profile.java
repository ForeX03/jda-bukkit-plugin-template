package cc.dreamcode.dsmp.core.profile;

import eu.okaeri.configs.annotation.CustomKey;
import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class Profile extends Document {

    @CustomKey("dm-enabled")
    private boolean dmEnabled = true;

    @CustomKey("ignore-list")
    private List<String> ignoreList = new ArrayList<>();

    public boolean hasIgnored(String player) {
        return this.ignoreList.contains(player);
    }

    public void addIgnore(String name) {
        this.ignoreList.add(name);
    }

    public void removeIgnore(String name) {
        this.ignoreList.remove(name);
    }

    public String getName() {
        return this.getPath().getValue();
    }
}
