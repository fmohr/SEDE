package ai.services.util;

import java.util.Objects;

/**
 * Mutable version of the {@link OptionalField OptionalField} class.
 *
 * Use {@link MutableOptionalField#set}, {@link MutableOptionalField#setNullable}, {@link MutableOptionalField#unset}
 * to change the capsulated value.
 */
public class MutableOptionalField<T> extends OptionalField<T> {

    /**
     * Constructs an empty instance.
     *
     */
    protected MutableOptionalField() {
        this.value = null;
    }

    /**
     * Returns an empty {@code MutableOptionalField} instance.  No value is present for this
     * MutableOptionalField.
     *
     * @param <T> Type of the non-existent value
     * @return an empty {@code MutableOptionalField}
     */
    public static<T> MutableOptionalField<T> empty() {
        return new MutableOptionalField<>();
    }

    /**
     * Constructs an instance with the value present.
     *
     * @param value the non-null value to be present
     * @throws NullPointerException if value is null
     */
    protected MutableOptionalField(T value) {
        this.value = Objects.requireNonNull(value);
    }

    /**
     * Returns an {@code MutableOptionalField} with the specified present non-null value.
     *
     * @param <T> the class of the value
     * @param value the value to be present, which must be non-null
     * @return an {@code MutableOptionalField} with the value present
     * @throws NullPointerException if value is null
     */
    public static <T> MutableOptionalField<T> of(T value) {
        return new MutableOptionalField<>(value);
    }

    /**
     * Returns an {@code MutableOptionalField} describing the specified value, if non-null,
     * otherwise returns an empty {@code MutableOptionalField}.
     *
     * @param <T> the class of the value
     * @param value the possibly-null value to describe
     * @return an {@code MutableOptionalField} with a present value if the specified value
     * is non-null, otherwise an empty {@code MutableOptionalField}
     */
    public static <T> MutableOptionalField<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    /**
     * Sets the inner value of this instance to the given non-null value.
     * After calling this method, {@link MutableOptionalField#isPresent()} always returns true.
     *
     * @param value new non-null value of this instance.
     */
    public void set(T value) {
        this.value = Objects.requireNonNull(value);
    }

    /**
     * Clears the value of this instance.
     * After calling this method, {@link MutableOptionalField#isPresent()} always returns false.
     */
    public void unset() {
        this.value = null;
    }

    /**
     * Sets the inner value of this instance to the given value.
     * Calling this method with {@code null} is allowed.
     * @param value The new inner value of this instance.
     */
    public void setNullable(T value) {
        this.value = value;
    }

}
