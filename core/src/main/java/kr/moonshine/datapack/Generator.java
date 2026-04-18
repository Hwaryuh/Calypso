package kr.moonshine.datapack;

import java.util.List;

public abstract class Generator {

    public abstract void writeTo(DatapackWriter writer, String namespace, MinecraftVersion version);

    public void validate(MinecraftVersion version) {
    }

    public abstract static class Builder<G extends Generator, B extends Builder<G, B>> {

        protected abstract List<Binding<?>> requiredBindings();

        protected abstract G buildInternal();

        public final G build() {
            for (Binding<?> binding : requiredBindings()) {
                binding.validate();
            }
            return buildInternal();
        }
    }
}
