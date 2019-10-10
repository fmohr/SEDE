package de.upb.sede;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link IQualifiable}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code Qualifiable.builder()}.
 */
@Generated(from = "IQualifiable", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class Qualifiable implements IQualifiable {
  private final String qualifier;
  private final String simpleName;

  private Qualifiable(Qualifiable.Builder builder) {
    this.qualifier = builder.qualifier;
    this.simpleName = builder.simpleName != null
        ? builder.simpleName
        : Objects.requireNonNull(IQualifiable.super.getSimpleName(), "simpleName");
  }

  private Qualifiable(String qualifier, String simpleName) {
    this.qualifier = qualifier;
    this.simpleName = simpleName;
  }

  /**
   * @return The value of the {@code qualifier} attribute
   */
  @JsonProperty("qualifier")
  @Override
  public String getQualifier() {
    return qualifier;
  }

  /**
   * @return The value of the {@code simpleName} attribute
   */
  @JsonProperty("simpleName")
  @Override
  public String getSimpleName() {
    return simpleName;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IQualifiable#getQualifier() qualifier} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for qualifier
   * @return A modified copy of the {@code this} object
   */
  public final Qualifiable withQualifier(String value) {
    String newValue = Objects.requireNonNull(value, "qualifier");
    if (this.qualifier.equals(newValue)) return this;
    return new Qualifiable(newValue, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IQualifiable#getSimpleName() simpleName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for simpleName
   * @return A modified copy of the {@code this} object
   */
  public final Qualifiable withSimpleName(String value) {
    String newValue = Objects.requireNonNull(value, "simpleName");
    if (this.simpleName.equals(newValue)) return this;
    return new Qualifiable(this.qualifier, newValue);
  }

  /**
   * This instance is equal to all instances of {@code Qualifiable} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof Qualifiable
        && equalTo((Qualifiable) another);
  }

  private boolean equalTo(Qualifiable another) {
    return qualifier.equals(another.qualifier)
        && simpleName.equals(another.simpleName);
  }

  /**
   * Computes a hash code from attributes: {@code qualifier}, {@code simpleName}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + qualifier.hashCode();
    h += (h << 5) + simpleName.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code Qualifiable} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("Qualifiable")
        .omitNullValues()
        .add("qualifier", qualifier)
        .add("simpleName", simpleName)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IQualifiable", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IQualifiable {
    @Nullable String qualifier;
    @Nullable String simpleName;
    @JsonProperty("qualifier")
    public void setQualifier(String qualifier) {
      this.qualifier = qualifier;
    }
    @JsonProperty("simpleName")
    public void setSimpleName(String simpleName) {
      this.simpleName = simpleName;
    }
    @Override
    public String getQualifier() { throw new UnsupportedOperationException(); }
    @Override
    public String getSimpleName() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static Qualifiable fromJson(Json json) {
    Qualifiable.Builder builder = Qualifiable.builder();
    if (json.qualifier != null) {
      builder.qualifier(json.qualifier);
    }
    if (json.simpleName != null) {
      builder.simpleName(json.simpleName);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IQualifiable} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable Qualifiable instance
   */
  public static Qualifiable copyOf(IQualifiable instance) {
    if (instance instanceof Qualifiable) {
      return (Qualifiable) instance;
    }
    return Qualifiable.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link Qualifiable Qualifiable}.
   * <pre>
   * Qualifiable.builder()
   *    .qualifier(String) // required {@link IQualifiable#getQualifier() qualifier}
   *    .simpleName(String) // optional {@link IQualifiable#getSimpleName() simpleName}
   *    .build();
   * </pre>
   * @return A new Qualifiable builder
   */
  public static Qualifiable.Builder builder() {
    return new Qualifiable.Builder();
  }

  /**
   * Builds instances of type {@link Qualifiable Qualifiable}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IQualifiable", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_QUALIFIER = 0x1L;
    private long initBits = 0x1L;

    private @Nullable String qualifier;
    private @Nullable String simpleName;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableQualifiable} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableQualifiable instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.qualifierIsSet()) {
        qualifier(instance.getQualifier());
      }
      simpleName(instance.getSimpleName());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IQualifiable} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IQualifiable instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableQualifiable) {
        return from((MutableQualifiable) instance);
      }
      qualifier(instance.getQualifier());
      simpleName(instance.getSimpleName());
      return this;
    }

    /**
     * Initializes the value for the {@link IQualifiable#getQualifier() qualifier} attribute.
     * @param qualifier The value for qualifier 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("qualifier")
    public final Builder qualifier(String qualifier) {
      this.qualifier = Objects.requireNonNull(qualifier, "qualifier");
      initBits &= ~INIT_BIT_QUALIFIER;
      return this;
    }

    /**
     * Initializes the value for the {@link IQualifiable#getSimpleName() simpleName} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IQualifiable#getSimpleName() simpleName}.</em>
     * @param simpleName The value for simpleName 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("simpleName")
    public final Builder simpleName(String simpleName) {
      this.simpleName = Objects.requireNonNull(simpleName, "simpleName");
      return this;
    }

    /**
     * Builds a new {@link Qualifiable Qualifiable}.
     * @return An immutable instance of Qualifiable
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public Qualifiable build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new Qualifiable(this);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_QUALIFIER) != 0) attributes.add("qualifier");
      return "Cannot build Qualifiable, some of required attributes are not set " + attributes;
    }
  }
}
