package kr.moonshine.datapack.dsl.trim

import kr.moonshine.datapack.dsl.DatapackDsl
import kr.moonshine.datapack.trim.ArmorMaterial
import kr.moonshine.datapack.trim.TrimMaterial
import kr.moonshine.datapack.trim.TrimPattern

fun DatapackDsl.trimMaterial(
    fileName: String,
    block: TrimMaterial.Builder.() -> Unit,
) {
    builder.add(TrimMaterial.builder(fileName).apply(block).build())
}

fun DatapackDsl.trimPattern(
    fileName: String,
    block: TrimPattern.Builder.() -> Unit,
) {
    builder.add(TrimPattern.builder(fileName).apply(block).build())
}

fun TrimMaterial.Builder.overrideArmorMaterials(block: MutableMap<ArmorMaterial, String>.() -> Unit) {
    overrideArmorMaterials(mutableMapOf<ArmorMaterial, String>().apply(block))
}

fun TrimMaterial.Builder.overrideArmorAssets(block: MutableMap<String, String>.() -> Unit) {
    overrideArmorAssets(mutableMapOf<String, String>().apply(block))
}
