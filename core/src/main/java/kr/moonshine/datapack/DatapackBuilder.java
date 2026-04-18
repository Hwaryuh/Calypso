package kr.moonshine.datapack;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.List;

public final class DatapackBuilder {
    private static final Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    private final String namespace;
    private final MinecraftVersion version;
    private final PackMeta packMeta;
    private final List<Generator> generators = Lists.newArrayList();

    private DatapackBuilder(String namespace, MinecraftVersion version, String description) {
        this.namespace = namespace;
        this.version = version;
        this.packMeta = PackMeta.of(description);
    }

    public DatapackBuilder add(Generator generator) {
        generator.validate(version);
        generators.add(generator);
        return this;
    }

    public Datapack build() {
        return new Datapack(entries());
    }

    private ImmutableMap<@NotNull String, byte @NotNull []> entries() {
        ImmutableMap.Builder<@NotNull String, byte @NotNull []> builder = ImmutableMap.builder();
        builder.put("pack.mcmeta", serialize(packMeta.toJson(version)));
        for (Generator generator : generators) {
            builder.put(generator.entryPath(namespace), serialize(generator));
        }
        return builder.build();
    }

    private byte[] serialize(Generator generator) {
        return serialize(generator.toJson(version));
    }

    private byte[] serialize(com.google.gson.JsonElement json) {
        return GSON.toJson(json).getBytes(StandardCharsets.UTF_8);
    }

    public static DatapackBuilder of(String namespace, MinecraftVersion version, String description) {
        return new DatapackBuilder(namespace, version, description);
    }

    public static DatapackBuilder of(String namespace, MinecraftVersion version) {
        return new DatapackBuilder(namespace, version, "");
    }
}
