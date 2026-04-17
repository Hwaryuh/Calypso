package kr.moonshine.datapack.trim;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kr.moonshine.datapack.Generator;
import kr.moonshine.datapack.util.JsonHelper;
import kr.moonshine.datapack.MinecraftVersion;
import kr.moonshine.datapack.ResourceLocation;
import net.kyori.adventure.text.Component;

import java.util.Map;

public final class TrimMaterial extends Generator {

    private final String fileName;
    private final String assetName;
    private final Component description;
    private final ResourceLocation ingredient;
    private final Float itemModelIndex;
    private final Map<ArmorMaterial, String> overrideArmorMaterials;
    private final Map<String, String> overrideArmorAssets;

    private TrimMaterial(Builder builder) {
        this.fileName = builder.fileName;
        this.assetName = builder.assetName;
        this.description = builder.description;
        this.ingredient = builder.ingredient;
        this.itemModelIndex = builder.itemModelIndex;
        this.overrideArmorMaterials = builder.overrideArmorMaterials;
        this.overrideArmorAssets = builder.overrideArmorAssets;
    }

    @Override
    public String entryPath(String namespace) {
        return "data/%s/trim_material/%s.json".formatted(namespace, fileName);
    }

    @Override
    protected JsonElement toJson(MinecraftVersion version) {
        JsonObject obj = new JsonObject();
        obj.addProperty("asset_name", assetName);
        obj.add("description", serializeComponent(description));

        JsonHelper.addVersioned(
            obj,
            "ingredient",
            ingredient != null ? ingredient.toString() : null,
            version,
            null,
            MinecraftVersion.V1_21_5
        );

        JsonHelper.addVersioned(
            obj,
            "item_model_index",
            itemModelIndex,
            version,
            null,
            MinecraftVersion.V1_21_4
        );

        if (overrideArmorMaterials != null) {
            MinecraftVersion.require(version, null, MinecraftVersion.V1_21_4, "override_armor_materials");
            JsonObject overrides = new JsonObject();
            overrideArmorMaterials.forEach((mat, palette) -> overrides.addProperty(mat.getId(), palette));
            obj.add("override_armor_materials", overrides);
        }

        if (overrideArmorAssets != null) {
            MinecraftVersion.require(version, MinecraftVersion.V1_21_4, null, "override_armor_assets");
            JsonObject overrides = new JsonObject();
            overrideArmorAssets.forEach(overrides::addProperty);
            obj.add("override_armor_assets", overrides);
        }

        return obj;
    }

    @Override
    public void validate(MinecraftVersion version) {
        if (assetName == null) throw new IllegalStateException("assetName is required");
        if (description == null) throw new IllegalStateException("description is required");
    }

    public static Builder builder(String fileName) {
        return new Builder(fileName);
    }

    public static final class Builder {
        private final String fileName;
        private String assetName;
        private Component description;
        private ResourceLocation ingredient;
        private Float itemModelIndex;
        private Map<ArmorMaterial, String> overrideArmorMaterials;
        private Map<String, String> overrideArmorAssets;

        private Builder(String fileName) {
            this.fileName = fileName;
        }

        public Builder assetName(String assetName) {
            this.assetName = assetName;
            return this;
        }

        public Builder description(Component description) {
            this.description = description;
            return this;
        }

        public Builder ingredient(ResourceLocation ingredient) {
            this.ingredient = ingredient;
            return this;
        }

        public Builder itemModelIndex(float itemModelIndex) {
            this.itemModelIndex = itemModelIndex;
            return this;
        }

        public Builder overrideArmorMaterials(Map<ArmorMaterial, String> overrides) {
            this.overrideArmorMaterials = Maps.newLinkedHashMap(overrides);
            return this;
        }

        public Builder overrideArmorAssets(Map<String, String> overrides) {
            this.overrideArmorAssets = Maps.newLinkedHashMap(overrides);
            return this;
        }

        public TrimMaterial build() {
            return new TrimMaterial(this);
        }
    }
}
