package kr.moonshine.datapack;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public final class PackMeta extends Generator {

    private final String description;

    private PackMeta(String description) {
        this.description = description;
    }

    public static PackMeta of(String description) {
        return new PackMeta(description);
    }

    @Override
    public String entryPath(String namespace) {
        return "pack.mcmeta";
    }

    @Override
    protected JsonElement toJson(MinecraftVersion version) {
        JsonObject pack = new JsonObject();
        pack.add("pack_format", DatapackFormats.of(version).toJson());
        pack.addProperty("description", description);

        JsonObject root = new JsonObject();
        root.add("pack", pack);
        return root;
    }
}
