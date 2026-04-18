package kr.moonshine.datapack;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * Minecraft version.
 *
 * @param major title
 * @param minor main update
 * @param patch minor update
 */
public record MinecraftVersion(int major, int minor, int patch) implements Comparable<MinecraftVersion> {

    public static final MinecraftVersion V1_21_4 = of(1, 21, 4);
    public static final MinecraftVersion V1_21_5 = of(1, 21, 5);
    public static final MinecraftVersion V1_21_6 = of(1, 21, 6);
    public static final MinecraftVersion V1_21_7 = of(1, 21, 7);
    public static final MinecraftVersion V1_21_8 = of(1, 21, 8);
    public static final MinecraftVersion V1_21_9 = of(1, 21, 9);
    public static final MinecraftVersion V1_21_10 = of(1, 21, 10);
    public static final MinecraftVersion V1_21_11 = of(1, 21, 11);

    private static final Comparator<MinecraftVersion> COMPARATOR = Comparator
        .comparingInt(MinecraftVersion::major)
        .thenComparingInt(MinecraftVersion::minor)
        .thenComparingInt(MinecraftVersion::patch);

    public static @NotNull MinecraftVersion of(int major, int minor, int patch) {
        return new MinecraftVersion(major, minor, patch);
    }

    public static @NotNull MinecraftVersion parse(@NotNull String version) {
        String[] parts = version.split("\\.");
        if (parts.length < 2 || parts.length > 3) throw new IllegalArgumentException("Invalid Minecraft version: " + version);

        int major = Integer.parseInt(parts[0]);
        int minor = Integer.parseInt(parts[1]);
        int patch = parts.length == 3 ? Integer.parseInt(parts[2]) : 0;

        return of(major, minor, patch);
    }

    public boolean isAtLeast(@NotNull MinecraftVersion other) {
        return compareTo(other) >= 0;
    }

    public boolean isLessThan(@NotNull MinecraftVersion other) {
        return compareTo(other) < 0;
    }

    /**
     * @param since inclusive — throws if current &lt; since
     * @param until exclusive — throws if current &gt;= until
     */
    public static void require(@NotNull MinecraftVersion current, MinecraftVersion since, MinecraftVersion until, @NotNull String field) {
        if (since != null && current.isLessThan(since))
            throw new IllegalStateException(
                "Field '" + field + "' is only available since " + since + ", but current version is " + current
            );
        if (until != null && current.isAtLeast(until))
            throw new IllegalStateException(
                "Field '" + field + "' was removed in " + until + ", but current version is " + current
            );
    }

    @Override
    public int compareTo(@NotNull MinecraftVersion other) {
        return COMPARATOR.compare(this, other);
    }

    @Override
    public @NotNull String toString() {
        return major + "." + minor + "." + patch;
    }
}
