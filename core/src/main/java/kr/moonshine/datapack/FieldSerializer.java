package kr.moonshine.datapack;

import com.google.gson.JsonElement;

@FunctionalInterface
public interface FieldSerializer<T> {
    JsonElement serialize(T value);
}
