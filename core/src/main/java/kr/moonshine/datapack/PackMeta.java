package kr.moonshine.datapack;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.kyori.adventure.text.Component;

public final class PackMeta {

    private final JsonElement description;

    private PackMeta(JsonElement description) {
        this.description = description;
    }

    public static PackMeta of(String description) {
        return new PackMeta(ComponentSerializer.serialize(Component.text(description)));
    }

    public static PackMeta of(Component description) {
        return new PackMeta(ComponentSerializer.serialize(description));
    }

    public JsonElement toJson(MinecraftVersion version) {
        JsonObject pack = new JsonObject();

        if (version.isAtLeast(MinecraftVersion.V1_21_9)) {
            DatapackFormat format = DatapackFormats.of(version);
            pack.add("min_format", toArray(format));
            pack.add("max_format", toArray(format));
        } else {
            pack.add("pack_format", DatapackFormats.of(version).toJson());
        }

        pack.add("description", description);

        JsonObject root = new JsonObject();
        root.add("pack", pack);
        return root;
    }

    private static JsonArray toArray(DatapackFormat format) {
        JsonArray arr = new JsonArray();
        arr.add(format.major());
        arr.add(format.minor());
        return arr;
    }
}
