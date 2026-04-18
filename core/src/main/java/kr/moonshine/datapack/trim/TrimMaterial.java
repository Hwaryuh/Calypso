package kr.moonshine.datapack.trim;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kr.moonshine.datapack.Generator;
import kr.moonshine.datapack.JsonField;
import kr.moonshine.datapack.MinecraftVersion;
import kr.moonshine.datapack.RequiredJsonField;
import kr.moonshine.datapack.ResourceLocation;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.Map;

public final class TrimMaterial extends Generator {

    private static final String ENTRY_PATH = "data/%s/trim_material/%s.json";

    private static final RequiredJsonField<String> ASSET_NAME = RequiredJsonField.ofString("asset_name");
    private static final RequiredJsonField<JsonElement> DESCRIPTION = RequiredJsonField.ofElement("description");
    private static final JsonField<String> INGREDIENT =
        JsonField.ofString(
            "ingredient",
            null,
            MinecraftVersion.V1_21_5
        );
    private static final JsonField<Number> ITEM_MODEL_INDEX =
        JsonField.ofNumber(
            "item_model_index",
            null,
            MinecraftVersion.V1_21_4
        );
    private static final JsonField<Map<ArmorMaterial, String>> OVERRIDE_ARMOR_MATERIALS =
        JsonField.of(
            "override_armor_materials",
            map -> {
                JsonObject obj = new JsonObject();
                map.forEach((mat, palette) -> obj.addProperty(mat.getId(), palette));
                return obj;
            },
            null, MinecraftVersion.V1_21_4
        );
    private static final JsonField<Map<String, String>> OVERRIDE_ARMOR_ASSETS =
        JsonField.of(
            "override_armor_assets",
            map -> {
                JsonObject obj = new JsonObject();
                map.forEach(obj::addProperty);
                return obj;
            },
            MinecraftVersion.V1_21_4, null
        );

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
        return ENTRY_PATH.formatted(namespace, fileName);
    }

    @Override
    protected JsonElement toJson(MinecraftVersion version) {
        JsonObject obj = new JsonObject();
        ASSET_NAME.write(obj, assetName);
        DESCRIPTION.write(obj, serializeComponent(description));
        INGREDIENT.write(obj, ingredient != null ? ingredient.toString() : null, version);
        ITEM_MODEL_INDEX.write(obj, itemModelIndex, version);
        OVERRIDE_ARMOR_MATERIALS.write(obj, overrideArmorMaterials, version);
        OVERRIDE_ARMOR_ASSETS.write(obj, overrideArmorAssets, version);
        return obj;
    }

    public static Builder builder(String fileName) {
        return new Builder(fileName);
    }

    public static final class Builder extends Generator.Builder<TrimMaterial, Builder> {

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

        @Override
        protected List<RequiredJsonField.Binding<?>> requiredBindings() {
            return List.of(
                ASSET_NAME.bind(assetName),
                DESCRIPTION.bind(description != null ? serializeComponent(description) : null)
            );
        }

        @Override
        protected TrimMaterial buildInternal() {
            return new TrimMaterial(this);
        }
    }
}
