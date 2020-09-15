package ai.services.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import java.io.IOException;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 *
 * Based on the {@link java.util.Optional} class with the distinction that it is used as a class or instance variable.
 * {@code OptionalField} instances are immutable. Use {@link MutableOptionalField} for mutable Optional fields.
 */
@JsonDeserialize(using = OptionalField.UnwrapDeserializer.class)
@JsonSerialize(using = OptionalField.UnwrapSerializer.class)
public class OptionalField<T> implements Serializable {

    /**
     * Common instance for {@code empty()}.
     */
    private static final OptionalField<?> EMPTY = new OptionalField<>();

    /**
     * If non-null, the value; if null, indicates no value is present
     */
    protected T value;

    /**
     * Constructs an empty instance.
     *
     * @implNote Generally only one empty instance, {@link OptionalField#EMPTY},
     * should exist per VM.
     */
    protected OptionalField() {
        this.value = null;
    }

    /**
     * Returns an empty {@code OptionalField} instance.  No value is present for this
     * OptionalField.
     *
     * @apiNote Though it may be tempting to do so, avoid testing if an object
     * is empty by comparing with {@code ==} against instances returned by
     * {@code Option.empty()}. There is no guarantee that it is a singleton.
     * Instead, use {@link #isPresent()}.
     *
     * @param <T> Type of the non-existent value
     * @return an empty {@code OptionalField}
     */
    public static<T> OptionalField<T> empty() {
        @SuppressWarnings("unchecked")
        OptionalField<T> t = (OptionalField<T>) EMPTY;
        return t;
    }

    /**
     * Constructs an instance with the value present.
     *
     * @param value the non-null value to be present
     * @throws NullPointerException if value is null
     */
    protected OptionalField(T value) {
        this.value = Objects.requireNonNull(value);
    }

    /**
     * Returns an {@code OptionalField} with the specified present non-null value.
     *
     * @param <T> the class of the value
     * @param value the value to be present, which must be non-null
     * @return an {@code OptionalField} with the value present
     * @throws NullPointerException if value is null
     */
    public static <T> OptionalField<T> of(T value) {
        return new OptionalField<>(value);
    }

    /**
     * Returns an {@code OptionalField} describing the specified value, if non-null,
     * otherwise returns an empty {@code OptionalField}.
     *
     * @param <T> the class of the value
     * @param value the possibly-null value to describe
     * @return an {@code OptionalField} with a present value if the specified value
     * is non-null, otherwise an empty {@code OptionalField}
     */
    public static <T> OptionalField<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    /**
     * If a value is present in this {@code OptionalField}, returns the value,
     * otherwise throws {@code NoSuchElementException}.
     *
     * @return the non-null value held by this {@code OptionalField}
     * @throws NoSuchElementException if there is no value present
     *
     * @see OptionalField#isPresent()
     */
    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    /**
     * Return {@code true} if there is a value present, otherwise {@code false}.
     *
     * @return {@code true} if there is a value present, otherwise {@code false}
     */
    public boolean isPresent() {
        return value != null;
    }

    /**
     * Return {@code true} if there is no value present, otherwise {@code false}.
     *
     * @return {@code true} if there is no value present, otherwise {@code false}
     */
    public boolean isAbsent() {
        return value == null;
    }

    /**
     * If a value is present, invoke the specified consumer with the value,
     * otherwise do nothing.
     *
     * @param consumer block to be executed if a value is present
     * @throws NullPointerException if value is present and {@code consumer} is
     * null
     */
    public void ifPresent(Consumer<? super T> consumer) {
        if (value != null)
            consumer.accept(value);
    }

    /**
     * If a value is present, and the value matches the given predicate,
     * return an {@code OptionalField} describing the value, otherwise return an
     * empty {@code OptionalField}.
     *
     * @param predicate a predicate to apply to the value, if present
     * @return an {@code OptionalField} describing the value of this {@code OptionalField}
     * if a value is present and the value matches the given predicate,
     * otherwise an empty {@code OptionalField}
     * @throws NullPointerException if the predicate is null
     */
    public OptionalField<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent())
            return this;
        else
            return predicate.test(value) ? this : empty();
    }

    /**
     * If a value is present, apply the provided mapping function to it,
     * and if the result is non-null, return an {@code OptionalField} describing the
     * result.  Otherwise return an empty {@code OptionalField}.
     *
     * @apiNote This method supports post-processing on optional values, without
     * the need to explicitly check for a return status.  For example, the
     * following code traverses a stream of file names, selects one that has
     * not yet been processed, and then opens that file, returning an
     * {@code OptionalField<FileInputStream>}:
     *
     * <pre>{@code
     *     OptionalField<FileInputStream> fis =
     *         names.stream().filter(name -> !isProcessedYet(name))
     *                       .findFirst()
     *                       .map(name -> new FileInputStream(name));
     * }</pre>
     *
     * Here, {@code findFirst} returns an {@code OptionalField<String>}, and then
     * {@code map} returns an {@code OptionalField<FileInputStream>} for the desired
     * file if one exists.
     *
     * @param <U> The type of the result of the mapping function
     * @param mapper a mapping function to apply to the value, if present
     * @return an {@code OptionalField} describing the result of applying a mapping
     * function to the value of this {@code OptionalField}, if a value is present,
     * otherwise an empty {@code OptionalField}
     * @throws NullPointerException if the mapping function is null
     */
    public<U> OptionalField<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent())
            return empty();
        else {
            return OptionalField.ofNullable(mapper.apply(value));
        }
    }

    /**
     * If a value is present, apply the provided {@code OptionalField}-bearing
     * mapping function to it, return that result, otherwise return an empty
     * {@code OptionalField}.  This method is similar to {@link #map(Function)},
     * but the provided mapper is one whose result is already an {@code OptionalField},
     * and if invoked, {@code flatMap} does not wrap it with an additional
     * {@code OptionalField}.
     *
     * @param <U> The type parameter to the {@code OptionalField} returned by
     * @param mapper a mapping function to apply to the value, if present
     *           the mapping function
     * @return the result of applying an {@code OptionalField}-bearing mapping
     * function to the value of this {@code OptionalField}, if a value is present,
     * otherwise an empty {@code OptionalField}
     * @throws NullPointerException if the mapping function is null or returns
     * a null result
     */
    public<U> OptionalField<U> flatMap(Function<? super T, OptionalField<U>> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent())
            return empty();
        else {
            return Objects.requireNonNull(mapper.apply(value));
        }
    }

    /**
     * Return the value if present, otherwise return {@code other}.
     *
     * @param other the value to be returned if there is no value present, may
     * be null
     * @return the value, if present, otherwise {@code other}
     */
    public T orElse(T other) {
        return value != null ? value : other;
    }

    /**
     * Return the value if present, otherwise invoke {@code other} and return
     * the result of that invocation.
     *
     * @param other a {@code Supplier} whose result is returned if no value
     * is present
     * @return the value if present otherwise the result of {@code other.get()}
     * @throws NullPointerException if value is not present and {@code other} is
     * null
     */
    public T orElseGet(Supplier<? extends T> other) {
        return value != null ? value : other.get();
    }

    /**
     * Return the contained value, if present, otherwise throw an exception
     * to be created by the provided supplier.
     *
     * @apiNote A method reference to the exception constructor with an empty
     * argument list can be used as the supplier. For example,
     * {@code IllegalStateException::new}
     *
     * @param <X> Type of the exception to be thrown
     * @param exceptionSupplier The supplier which will return the exception to
     * be thrown
     * @return the present value
     * @throws X if there is no value present
     * @throws NullPointerException if no value is present and
     * {@code exceptionSupplier} is null
     */
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    /**
     * Indicates whether some other object is "equal to" this OptionalField. The
     * other object is considered equal if:
     * <ul>
     * <li>it is also an {@code OptionalField} and;
     * <li>both instances have no value present or;
     * <li>the present values are "equal to" each other via {@code equals()}.
     * </ul>
     *
     * @param obj an object to be tested for equality
     * @return {code true} if the other object is "equal to" this object
     * otherwise {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof OptionalField)) {
            return false;
        }

        OptionalField<?> other = (OptionalField<?>) obj;
        return Objects.equals(value, other.value);
    }

    /**
     * Returns the hash code value of the present value, if any, or 0 (zero) if
     * no value is present.
     *
     * @return hash code value of the present value or 0 if no value is present
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    /**
     * Returns a non-empty string representation of this OptionalField suitable for
     * debugging. The exact presentation format is unspecified and may vary
     * between implementations and versions.
     *
     * @implSpec If a value is present the result must include its string
     * representation in the result. Empty and present OptionalFields must be
     * unambiguously differentiable.
     *
     * @return the string representation of this instance
     */
    @Override
    public String toString() {
        return value != null
                ? String.format("OptionalField[%s]", value)
                : "OptionalField.empty";
    }

    /**
     * Converts this instance to an optional.
     * @return Returns an optional that takes the value of this instance.
     */
    public Optional<T> opt() {
        return Optional.ofNullable(value);
    }

    /**
     * Converts the given  {@link java.util.Optional Optional} instance to an Optional field instance.
     * @param opt Optional instance
     * @param <T> the Type of the given optional
     * @return a Optional field
     */
    public static <T> OptionalField<T> of(Optional<T> opt) {
        return opt.map(OptionalField::of).orElse((OptionalField<T>) EMPTY);
    }

    static class UnwrapDeserializer extends JsonDeserializer<MutableOptionalField> implements
            ContextualDeserializer {

        private Class<?> wrappedType;

        public JsonDeserializer<?> createContextual(DeserializationContext ctxt,
                                                    BeanProperty property) throws JsonMappingException {
            JavaType collectionType = property.getType();
            JavaType collectedType = collectionType.containedType(0);
            wrappedType = collectedType.getRawClass();
            return this;
        }

        @Override
        public MutableOptionalField deserialize(JsonParser parser, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            Object field = parser.getCodec().readValue(parser, wrappedType);
            return MutableOptionalField.ofNullable(field);
        }
    }

    static class UnwrapSerializer extends JsonSerializer<OptionalField> {

        @Override
        public void serialize(OptionalField value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if(value.isPresent()) {
                gen.writeObject(value.get());
            } else {
                gen.writeNull();
            }
        }

    }
}
