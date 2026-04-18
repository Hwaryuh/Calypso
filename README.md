# Calypso

Code-driven Minecraft datapack generation with compile-time safety and multi-version support.

## Usage

### Java

```java
Datapack datapack = DatapackBuilder.of("your_namespace", MinecraftVersion.V1_21_5)
    .add(TrimMaterial.builder("ruby")
        .assetName("ruby")
        .description(Component.text("Ruby"))
        .ingredient(ResourceLocation.minecraft("ruby"))
        .build())
    .build();

datapack.toZip();           // byte[]
datapack.toFiles(path);     // write to files
```

### Kotlin DSL

```kotlin
    trimMaterial("ruby") {
        assetName("ruby")
        description(Component.text("Ruby"))
        ingredient(ResourceLocation.minecraft("ruby"))
    }
}

datapack.toZip()            // ByteArray
datapack.toFiles(path)      // write to files
```

## Supported Versions

Minecraft 1.21.4 ~ 1.21.11
