package kr.moonshine.datapack;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.common.collect.Maps;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

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

    private Map<String, byte[]> entries() {
        Map<String, byte[]> map = Maps.newLinkedHashMap();
        map.put(packMeta.entryPath(namespace), serialize(packMeta));
        for (Generator generator : generators) {
            map.put(generator.entryPath(namespace), serialize(generator));
        }
        return map;
    }

    private byte[] serialize(Generator generator) {
        return GSON.toJson(generator.toJson(version)).getBytes(StandardCharsets.UTF_8);
    }

    public static DatapackBuilder of(String namespace, MinecraftVersion version, String description) {
        return new DatapackBuilder(namespace, version, description);
    }

    public static DatapackBuilder of(String namespace, MinecraftVersion version) {
        return new DatapackBuilder(namespace, version, "");
    }
}
