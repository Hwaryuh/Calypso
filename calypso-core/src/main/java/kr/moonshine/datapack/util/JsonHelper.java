package kr.moonshine.datapack.util;

import com.google.gson.JsonObject;
import kr.moonshine.datapack.MinecraftVersion;

public final class JsonHelper {
    private JsonHelper() {
    }

    public static void addVersioned(JsonObject obj, String key, String value,
                                    MinecraftVersion version,
                                    MinecraftVersion since, MinecraftVersion until) {
        if (value == null) return;
        MinecraftVersion.require(version, since, until, key);
        obj.addProperty(key, value);
    }

    public static void addVersioned(JsonObject obj, String key, Number value,
                             MinecraftVersion version,
                             MinecraftVersion since, MinecraftVersion until) {
        if (value == null) return;
        MinecraftVersion.require(version, since, until, key);
        obj.addProperty(key, value);
    }
}
