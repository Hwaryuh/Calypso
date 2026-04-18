package kr.moonshine.datapack;

public final class FieldValue<T> {

    private final JsonField<T> field;
    private final T value;

    private FieldValue(JsonField<T> field, T value) {
        this.field = field;
        this.value = value;
    }

    public static <T> FieldValue<T> of(JsonField<T> field, T value) {
        return new FieldValue<>(field, value);
    }

    public void validateRequired() {
        field.validateRequired(value);
    }
}
