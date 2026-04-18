package kr.moonshine.datapack;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import org.jetbrains.annotations.NotNull;

public record DatapackFormat(
    int major,
    int minor
) {

    public static DatapackFormat of(int major) {
        return new DatapackFormat(major, 0);
    }

    public static DatapackFormat of(int major, int minor) {
        return new DatapackFormat(major, minor);
    }

    public @NotNull JsonElement toJson() {
        if (minor == 0) return new JsonPrimitive(major);
        return JsonParser.parseString(major + "." + minor);
    }

    @Override
    public @NotNull String toString() {
        return minor == 0 ? String.valueOf(major) : major + "." + minor;
    }
}
