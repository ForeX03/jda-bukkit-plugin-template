package cc.dreamcode.dsmp.core.whitelist.suggestion;

import cc.dreamcode.command.suggestion.supplier.SuggestionSupplier;
import cc.dreamcode.dsmp.core.whitelist.WhitelistService;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class WhitelistedPlayersSuggestion implements SuggestionSupplier {

    private final WhitelistService whitelistService;

    @Override
    public List<String> supply(@NonNull Class<?> paramType) {
        return this.whitelistService.getWhitelistedPlayers();
    }
}
