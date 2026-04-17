package kr.moonshine.datapack;

import org.jetbrains.annotations.NotNull;

public record ResourceLocation(
    String namespace,
    String path
) {

    public ResourceLocation {
        if (namespace.isEmpty()) throw new IllegalArgumentException("Namespace must not be empty");
        if (path.isEmpty()) throw new IllegalArgumentException("Path must not be empty");
    }

    @Override
    public @NotNull String toString() {
        return namespace + ":" + path;
    }

    public static ResourceLocation of(String namespace, String path) {
        return new ResourceLocation(namespace, path);
    }

    public static ResourceLocation of(String value) {
        String[] parts = value.split(":", 2);
        if (parts.length != 2)
            throw new IllegalArgumentException("Invalid ResourceLocation: '" + value + "'. Expected format 'namespace:path'");
        return new ResourceLocation(parts[0], parts[1]);
    }

    public static ResourceLocation minecraft(String path) {
        return new ResourceLocation("minecraft", path);
    }
}
