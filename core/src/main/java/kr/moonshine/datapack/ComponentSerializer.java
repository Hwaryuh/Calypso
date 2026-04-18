package kr.moonshine.datapack;

import com.google.gson.JsonElement;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

public final class ComponentSerializer {

    private ComponentSerializer() {
    }

    public static JsonElement serialize(Component component) {
        return GsonComponentSerializer.gson().serializeToTree(component);
    }
}
