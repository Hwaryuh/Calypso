package kr.moonshine.datapack.trim;

public enum ArmorMaterial {
    LEATHER("leather"),
    CHAINMAIL("chainmail"),
    IRON("iron"),
    GOLD("gold"),
    DIAMOND("diamond"),
    NETHERITE("netherite"),
    TURTLE("turtle"),
    ;

    private final String id;

    ArmorMaterial(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
