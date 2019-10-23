package de.upb.sede.composition.graphs.types;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link IPrimitiveValueType}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code PrimitiveValueType.builder()}.
 */
@Generated(from = "IPrimitiveValueType", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class PrimitiveValueType implements IPrimitiveValueType {
  private final String typeClass;

  private PrimitiveValueType(PrimitiveValueType.Builder builder) {
    this.typeClass = builder.typeClass != null
        ? builder.typeClass
        : Objects.requireNonNull(IPrimitiveValueType.super.getTypeClass(), "typeClass");
  }

  private PrimitiveValueType(String typeClass) {
    this.typeClass = typeClass;
  }

  /**
   * @return The value of the {@code typeClass} attribute
   */
  @JsonProperty("typeClass")
  @Override
  public String getTypeClass() {
    return typeClass;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IPrimitiveValueType#getTypeClass() typeClass} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for typeClass
   * @return A modified copy of the {@code this} object
   */
  public final PrimitiveValueType withTypeClass(String value) {
    String newValue = Objects.requireNonNull(value, "typeClass");
    if (this.typeClass.equals(newValue)) return this;
    return new PrimitiveValueType(newValue);
  }

  /**
   * This instance is equal to all instances of {@code PrimitiveValueType} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof PrimitiveValueType
        && equalTo((PrimitiveValueType) another);
  }

  private boolean equalTo(PrimitiveValueType another) {
    return typeClass.equals(another.typeClass);
  }

  /**
   * Computes a hash code from attributes: {@code typeClass}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + typeClass.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code PrimitiveValueType} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("PrimitiveValueType")
        .omitNullValues()
        .add("typeClass", typeClass)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IPrimitiveValueType", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IPrimitiveValueType {
    @Nullable String typeClass;
    @JsonProperty("typeClass")
    public void setTypeClass(String typeClass) {
      this.typeClass = typeClass;
    }
    @Override
    public String getTypeClass() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static PrimitiveValueType fromJson(Json json) {
    PrimitiveValueType.Builder builder = PrimitiveValueType.builder();
    if (json.typeClass != null) {
      builder.typeClass(json.typeClass);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link IPrimitiveValueType} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable PrimitiveValueType instance
   */
  public static PrimitiveValueType copyOf(IPrimitiveValueType instance) {
    if (instance instanceof PrimitiveValueType) {
      return (PrimitiveValueType) instance;
    }
    return PrimitiveValueType.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link PrimitiveValueType PrimitiveValueType}.
   * <pre>
   * PrimitiveValueType.builder()
   *    .typeClass(String) // optional {@link IPrimitiveValueType#getTypeClass() typeClass}
   *    .build();
   * </pre>
   * @return A new PrimitiveValueType builder
   */
  public static PrimitiveValueType.Builder builder() {
    return new PrimitiveValueType.Builder();
  }

  /**
   * Builds instances of type {@link PrimitiveValueType PrimitiveValueType}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IPrimitiveValueType", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private @Nullable String typeClass;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutablePrimitiveValueType} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutablePrimitiveValueType instance) {
      Objects.requireNonNull(instance, "instance");
      typeClass(instance.getTypeClass());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.composition.graphs.types.TypeClass} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(TypeClass instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.composition.graphs.types.IPrimitiveValueType} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IPrimitiveValueType instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof MutablePrimitiveValueType) {
        from((MutablePrimitiveValueType) object);
        return;
      }
      if (object instanceof TypeClass) {
        TypeClass instance = (TypeClass) object;
        typeClass(instance.getTypeClass());
      }
    }

    /**
     * Initializes the value for the {@link IPrimitiveValueType#getTypeClass() typeClass} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IPrimitiveValueType#getTypeClass() typeClass}.</em>
     * @param typeClass The value for typeClass 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("typeClass")
    public final Builder typeClass(String typeClass) {
      this.typeClass = Objects.requireNonNull(typeClass, "typeClass");
      return this;
    }

    /**
     * Builds a new {@link PrimitiveValueType PrimitiveValueType}.
     * @return An immutable instance of PrimitiveValueType
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public PrimitiveValueType build() {
      return new PrimitiveValueType(this);
    }
  }
}
