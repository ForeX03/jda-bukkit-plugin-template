package cc.dreamcode.dsmp.discord.jda.resolver;

import cc.dreamcode.platform.component.ComponentClassResolver;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class JDAListenerAdapterResolver implements ComponentClassResolver<ListenerAdapter> {

    private final JDA jda;

    public boolean isAssignableFrom(@NonNull Class<ListenerAdapter> type) {
        return ListenerAdapter.class.isAssignableFrom(type);
    }

    public String getComponentName() {
        return "bot listener";
    }

    public Map<String, Object> getMetas(@NonNull ListenerAdapter listenerAdapter) {
        return MapBuilder.of("handlers", Arrays.stream(listenerAdapter.getClass().getDeclaredMethods()).filter((method) -> method.getAnnotation(Override.class) != null).map(Method::getName).collect(Collectors.joining(", ")));
    }

    public ListenerAdapter resolve(@NonNull Injector injector, @NonNull Class<ListenerAdapter> type) {
        ListenerAdapter listenerAdapter = injector.createInstance(type);

        this.jda.addEventListener(listenerAdapter);

        return listenerAdapter;
    }
}
