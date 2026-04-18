package kr.moonshine.datapack;

import com.google.gson.JsonElement;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import java.util.List;

public abstract class Generator {

    public abstract String entryPath(String namespace);

    protected abstract JsonElement toJson(MinecraftVersion version);

    public void validate(MinecraftVersion version) {
    }

    protected static JsonElement serializeComponent(Component component) {
        return GsonComponentSerializer.gson().serializeToTree(component);
    }

    public abstract static class Builder<G extends Generator, B extends Builder<G, B>> {

        protected abstract List<RequiredJsonField.Binding<?>> requiredBindings();

        protected abstract G buildInternal();

        public final G build() {
            for (RequiredJsonField.Binding<?> binding : requiredBindings()) {
                binding.validate();
            }
            return buildInternal();
        }
    }
}
