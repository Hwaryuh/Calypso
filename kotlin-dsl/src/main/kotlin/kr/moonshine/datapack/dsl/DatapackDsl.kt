package kr.moonshine.datapack.dsl

import kr.moonshine.datapack.Datapack
import kr.moonshine.datapack.DatapackBuilder
import kr.moonshine.datapack.MinecraftVersion

fun datapack(
    namespace: String,
    version: MinecraftVersion,
    description: String = "",
    block: DatapackDsl.() -> Unit,
): Datapack = DatapackDsl(namespace, version, description).apply(block).builder.build()

class DatapackDsl(
    namespace: String,
    version: MinecraftVersion,
    description: String,
) {
    internal val builder = DatapackBuilder.of(namespace, version, description)
}
