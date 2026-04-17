package kr.moonshine.datapack;

import com.google.common.collect.Maps;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class DatapackFormats {

    private static final Map<MinecraftVersion, DatapackFormat> MAP = Maps.newTreeMap();

    static {
        MAP.put(MinecraftVersion.V1_21_4, DatapackFormat.of(61));
        MAP.put(MinecraftVersion.V1_21_5, DatapackFormat.of(71));
        MAP.put(MinecraftVersion.V1_21_6, DatapackFormat.of(80));
        MAP.put(MinecraftVersion.V1_21_7, DatapackFormat.of(81));
        MAP.put(MinecraftVersion.V1_21_8, DatapackFormat.of(81));
        MAP.put(MinecraftVersion.V1_21_9, DatapackFormat.of(88, 0));
        MAP.put(MinecraftVersion.V1_21_10, DatapackFormat.of(88, 0));
        MAP.put(MinecraftVersion.V1_21_11, DatapackFormat.of(94, 1));
    }

    private DatapackFormats() {
    }

    public static @NotNull DatapackFormat of(@NotNull MinecraftVersion version) {
        DatapackFormat format = MAP.get(version);
        if (format == null) throw new IllegalArgumentException("No DatapackFormat mapping for version " + version);
        return format;
    }
}
