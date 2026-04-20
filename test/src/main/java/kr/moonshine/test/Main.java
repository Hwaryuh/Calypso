package kr.moonshine.test;

import kr.moonshine.datapack.DatapackBuilder;
import kr.moonshine.datapack.MinecraftVersion;
import kr.moonshine.datapack.ResourceLocation;
import kr.moonshine.datapack.trim.TrimMaterial;
import kr.moonshine.datapack.trim.TrimPattern;
import net.kyori.adventure.text.Component;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
//        test(MinecraftVersion.V1_21_4);
//        test(MinecraftVersion.V1_21_5);
        test(MinecraftVersion.V1_21_11);
    }

    private static void test(MinecraftVersion version) {
        var datapack = DatapackBuilder.of("test", version)
            .add(TrimMaterial.builder("ruby")
                .assetName("ruby")
                .description(Component.text("Ruby"))
//                .ingredient(ResourceLocation.minecraft("ruby"))
                .build())
            .add(TrimPattern.builder("silence")
                .assetId(ResourceLocation.of("test", "silence"))
                .description(Component.text("Silence"))
                .build())
            .create();

        datapack.toFiles(Path.of("output"));
    }
}
