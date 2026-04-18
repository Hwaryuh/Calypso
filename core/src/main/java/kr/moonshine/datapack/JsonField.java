package kr.moonshine.datapack;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public final class JsonField<T> {

    private final String key;
    private final FieldSerializer<T> serializer;
    private final MinecraftVersion since;
    private final MinecraftVersion until;

    private JsonField(String key, FieldSerializer<T> serializer, MinecraftVersion since, MinecraftVersion until) {
        this.key = key;
        this.serializer = serializer;
        this.since = since;
        this.until = until;
    }

    public static <T> JsonField<T> of(String key, FieldSerializer<T> serializer) {
        return new JsonField<>(key, serializer, null, null);
    }

    public static <T> JsonField<T> of(String key, FieldSerializer<T> serializer, MinecraftVersion since, MinecraftVersion until) {
        return new JsonField<>(key, serializer, since, until);
    }

    public static JsonField<String> ofString(String key) {
        return of(key, JsonPrimitive::new);
    }

    public static JsonField<String> ofString(String key, MinecraftVersion since, MinecraftVersion until) {
        return of(key, JsonPrimitive::new, since, until);
    }

    public static JsonField<Number> ofNumber(String key) {
        return of(key, JsonPrimitive::new);
    }

    public static JsonField<Number> ofNumber(String key, MinecraftVersion since, MinecraftVersion until) {
        return of(key, JsonPrimitive::new, since, until);
    }

    public static JsonField<Boolean> ofBoolean(String key) {
        return of(key, JsonPrimitive::new);
    }

    public static JsonField<Boolean> ofBoolean(String key, MinecraftVersion since, MinecraftVersion until) {
        return of(key, JsonPrimitive::new, since, until);
    }

    public static JsonField<JsonElement> ofElement(String key) {
        return of(key, v -> v);
    }

    public static JsonField<JsonElement> ofElement(String key, MinecraftVersion since, MinecraftVersion until) {
        return of(key, v -> v, since, until);
    }

    public void write(JsonObject obj, T value) {
        if (value == null) return;
        obj.add(key, serializer.serialize(value));
    }

    public void write(JsonObject obj, T value, MinecraftVersion version) {
        if (value == null) return;
        MinecraftVersion.require(version, since, until, key);
        obj.add(key, serializer.serialize(value));
    }

    public String key() {
        return key;
    }
}
