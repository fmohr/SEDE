package de.upb.sede.util;

import java.util.Objects;

/**
 * Mutable version of the {@link WobblyField WobblyField} class.
 *
 * Use {@link MutableWobblyField#set}, {@link MutableWobblyField#setNullable}, {@link MutableWobblyField#unset}
 * to change the capsulated value.
 */
public class MutableWobblyField<T> extends WobblyField<T> {

    /**
     * Constructs an empty instance.
     *
     */
    protected MutableWobblyField() {
        this.value = null;
    }

    /**
     * Returns an empty {@code MutableWobblyField} instance.  No value is present for this
     * MutableWobblyField.
     *
     * @param <T> Type of the non-existent value
     * @return an empty {@code MutableWobblyField}
     */
    public static<T> MutableWobblyField<T> empty() {
        return new MutableWobblyField<>();
    }

    /**
     * Constructs an instance with the value present.
     *
     * @param value the non-null value to be present
     * @throws NullPointerException if value is null
     */
    protected MutableWobblyField(T value) {
        this.value = Objects.requireNonNull(value);
    }

    /**
     * Returns an {@code MutableWobblyField} with the specified present non-null value.
     *
     * @param <T> the class of the value
     * @param value the value to be present, which must be non-null
     * @return an {@code MutableWobblyField} with the value present
     * @throws NullPointerException if value is null
     */
    public static <T> MutableWobblyField<T> of(T value) {
        return new MutableWobblyField<>(value);
    }

    /**
     * Returns an {@code MutableWobblyField} describing the specified value, if non-null,
     * otherwise returns an empty {@code MutableWobblyField}.
     *
     * @param <T> the class of the value
     * @param value the possibly-null value to describe
     * @return an {@code MutableWobblyField} with a present value if the specified value
     * is non-null, otherwise an empty {@code MutableWobblyField}
     */
    public static <T> MutableWobblyField<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    /**
     * Sets the inner value of this instance to the given non-null value.
     * After calling this method, {@link MutableWobblyField#isPresent()} always returns true.
     *
     * @param value new non-null value of this instance.
     */
    public void set(T value) {
        this.value = Objects.requireNonNull(value);
    }

    /**
     * Clears the value of this instance.
     * After calling this method, {@link MutableWobblyField#isPresent()} always returns false.
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
