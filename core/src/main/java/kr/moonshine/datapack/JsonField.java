package kr.moonshine.datapack;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public final class JsonField<T> {

    private final String key;
    private final FieldSerializer<T> serializer;
    private final MinecraftVersion since;
    private final MinecraftVersion until;
    private final boolean required;

    private JsonField(String key, FieldSerializer<T> serializer, MinecraftVersion since, MinecraftVersion until, boolean required) {
        this.key = key;
        this.serializer = serializer;
        this.since = since;
        this.until = until;
        this.required = required;
    }

    public static <T> JsonField<T> of(String key, FieldSerializer<T> serializer) {
        return new JsonField<>(key, serializer, null, null, false);
    }

    public static <T> JsonField<T> of(String key, FieldSerializer<T> serializer, MinecraftVersion since, MinecraftVersion until) {
        return new JsonField<>(key, serializer, since, until, false);
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

    public JsonField<T> required() {
        return new JsonField<>(key, serializer, since, until, true);
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

    public void validateRequired(T value) {
        if (required && value == null) {
            throw new IllegalStateException("Field '" + key + "' is required");
        }
    }

    public String key() {
        return key;
    }

    public boolean isRequired() {
        return required;
    }
}
