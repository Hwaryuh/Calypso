package kr.moonshine.datapack.trim;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import kr.moonshine.datapack.ComponentSerializer;
import kr.moonshine.datapack.Generator;
import kr.moonshine.datapack.JsonField;
import kr.moonshine.datapack.MinecraftVersion;
import kr.moonshine.datapack.RequiredJsonField;
import kr.moonshine.datapack.ResourceLocation;
import net.kyori.adventure.text.Component;

import java.util.List;

public final class TrimPattern extends Generator {

    private static final String ENTRY_PATH = "data/%s/trim_pattern/%s.json";

    private static final RequiredJsonField<ResourceLocation> ASSET_ID =
        RequiredJsonField.of("asset_id", location -> new JsonPrimitive(location.toString()));
    private static final RequiredJsonField<Component> DESCRIPTION =
        RequiredJsonField.of("description", ComponentSerializer::serialize);
    private static final JsonField<String> TEMPLATE_ITEM =
        JsonField.ofString("template_item", null, MinecraftVersion.V1_21_5);
    private static final JsonField<Boolean> DECAL = JsonField.ofBoolean("decal");

    private final String fileName;
    private final ResourceLocation assetId;
    private final Component description;
    private final ResourceLocation templateItem;
    private final Boolean decal;

    private TrimPattern(Builder builder) {
        this.fileName = builder.fileName;
        this.assetId = builder.assetId;
        this.description = builder.description;
        this.templateItem = builder.templateItem;
        this.decal = builder.decal;
    }

    @Override
    public String entryPath(String namespace) {
        return ENTRY_PATH.formatted(namespace, fileName);
    }

    @Override
    protected JsonElement toJson(MinecraftVersion version) {
        JsonObject obj = new JsonObject();
        ASSET_ID.write(obj, assetId);
        DESCRIPTION.write(obj, description);
        TEMPLATE_ITEM.write(obj, templateItem != null ? templateItem.toString() : null, version);
        DECAL.write(obj, decal);
        return obj;
    }

    @Override
    public void validate(MinecraftVersion version) {
        if (templateItem != null && templateItem.equals(ResourceLocation.minecraft("air"))) {
            throw new IllegalStateException("template_item 'minecraft:air' is not allowed");
        }
    }

    public static Builder builder(String fileName) {
        return new Builder(fileName);
    }

    public static final class Builder extends Generator.Builder<TrimPattern, Builder> {

        private final String fileName;
        private ResourceLocation assetId;
        private Component description;
        private ResourceLocation templateItem;
        private Boolean decal;

        private Builder(String fileName) {
            this.fileName = fileName;
        }

        public Builder assetId(ResourceLocation assetId) {
            this.assetId = assetId;
            return this;
        }

        public Builder assetId(String namespace, String path) {
            this.assetId = ResourceLocation.of(namespace, path);
            return this;
        }

        public Builder description(Component description) {
            this.description = description;
            return this;
        }

        public Builder templateItem(ResourceLocation templateItem) {
            this.templateItem = templateItem;
            return this;
        }

        public Builder decal(boolean decal) {
            this.decal = decal;
            return this;
        }

        @Override
        protected List<RequiredJsonField.Binding<?>> requiredBindings() {
            return List.of(
                ASSET_ID.bind(assetId),
                DESCRIPTION.bind(description)
            );
        }

        @Override
        protected TrimPattern buildInternal() {
            return new TrimPattern(this);
        }
    }
}
