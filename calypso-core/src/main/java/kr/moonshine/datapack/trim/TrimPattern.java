package kr.moonshine.datapack.trim;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kr.moonshine.datapack.Generator;
import kr.moonshine.datapack.util.JsonHelper;
import kr.moonshine.datapack.MinecraftVersion;
import kr.moonshine.datapack.ResourceLocation;
import net.kyori.adventure.text.Component;

public final class TrimPattern extends Generator {

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
        return "data/%s/trim_pattern/%s.json".formatted(namespace, fileName);
    }

    @Override
    protected JsonElement toJson(MinecraftVersion version) {
        JsonObject obj = new JsonObject();
        obj.addProperty("asset_id", assetId.toString());
        obj.add("description", serializeComponent(description));

        JsonHelper.addVersioned(
            obj,
            "template_item",
            templateItem != null ? templateItem.toString() : null,
            version, null, MinecraftVersion.V1_21_5
        );

        if (decal != null) {
            obj.addProperty("decal", decal);
        }

        return obj;
    }

    public static Builder builder(String fileName) {
        return new Builder(fileName);
    }

    public static final class Builder {
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

        public TrimPattern build() {
            if (assetId == null) throw new IllegalStateException("assetId is required");
            if (description == null) throw new IllegalStateException("description is required");
            return new TrimPattern(this);
        }
    }
}
