package kr.moonshine.datapack;

import com.google.gson.JsonElement;

import java.util.List;

public abstract class Generator {

    public abstract String entryPath(String namespace);

    protected abstract JsonElement toJson(MinecraftVersion version);

    public void validate(MinecraftVersion version) {
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
