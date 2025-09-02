package cc.dreamcode.dsmp.core.verification;

import cc.dreamcode.dsmp.core.verification.command.VerificationCommand;
import cc.dreamcode.dsmp.core.verification.modal.VerificationModalOpenListener;
import cc.dreamcode.dsmp.core.verification.modal.VerificationModalSubmittedListener;
import cc.dreamcode.platform.component.ComponentService;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class VerificationProvider {
    private final ComponentService componentService;

    public void load() {
        this.componentService.registerComponent(VerificationCommand.class);
        this.componentService.registerComponent(VerificationModalOpenListener.class);
        this.componentService.registerComponent(VerificationModalSubmittedListener.class);
    }
}
