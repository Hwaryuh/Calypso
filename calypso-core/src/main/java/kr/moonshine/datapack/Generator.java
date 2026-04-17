package kr.moonshine.datapack;

import com.google.gson.JsonElement;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

public abstract class Generator {

    public abstract String entryPath(String namespace);

    protected abstract JsonElement toJson(MinecraftVersion version);

    public void validate(MinecraftVersion version) {
    }

    protected static JsonElement serializeComponent(Component component) {
        return GsonComponentSerializer.gson().serializeToTree(component);
    }
}
