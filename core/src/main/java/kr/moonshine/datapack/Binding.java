package kr.moonshine.datapack;

public final class Binding<T> {

    private final String key;
    private final T value;

    Binding(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public void validate() {
        if (value == null) {
            throw new IllegalStateException("Required field '" + key + "' is not set");
        }
    }
}
