package kr.moonshine.datapack;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public final class RequiredJsonField<T> {

    private final String key;
    private final FieldSerializer<T> serializer;

    private RequiredJsonField(String key, FieldSerializer<T> serializer) {
        this.key = key;
        this.serializer = serializer;
    }

    public static <T> RequiredJsonField<T> of(String key, FieldSerializer<T> serializer) {
        return new RequiredJsonField<>(key, serializer);
    }

    public static RequiredJsonField<String> ofString(String key) {
        return of(key, JsonPrimitive::new);
    }

    public static RequiredJsonField<Number> ofNumber(String key) {
        return of(key, JsonPrimitive::new);
    }

    public static RequiredJsonField<Boolean> ofBoolean(String key) {
        return of(key, JsonPrimitive::new);
    }

    public static RequiredJsonField<JsonElement> ofElement(String key) {
        return of(key, v -> v);
    }

    public void write(JsonObject obj, T value) {
        if (value == null) throw new IllegalStateException("Required field '" + key + "' is null");
        obj.add(key, serializer.serialize(value));
    }

    public Binding<T> bind(T value) {
        return new Binding<>(this, value);
    }

    public String key() {
        return key;
    }

    public static final class Binding<T> {

        private final RequiredJsonField<T> field;
        private final T value;

        private Binding(RequiredJsonField<T> field, T value) {
            this.field = field;
            this.value = value;
        }

        public void validate() {
            if (value == null) {
                throw new IllegalStateException("Required field '" + field.key() + "' is not set");
            }
        }
    }
}
