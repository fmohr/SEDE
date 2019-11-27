package de.upb.sede.composition.graphs.types;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.core.PrimitiveType;
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
  private final PrimitiveType primitiveType;

  private PrimitiveValueType(PrimitiveType primitiveType) {
    this.primitiveType = primitiveType;
  }

  /**
   * @return The value of the {@code primitiveType} attribute
   */
  @JsonProperty("primitiveType")
  @Override
  public PrimitiveType getPrimitiveType() {
    return primitiveType;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IPrimitiveValueType#getPrimitiveType() primitiveType} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for primitiveType
   * @return A modified copy of the {@code this} object
   */
  public final PrimitiveValueType withPrimitiveType(PrimitiveType value) {
    if (this.primitiveType == value) return this;
    PrimitiveType newValue = Objects.requireNonNull(value, "primitiveType");
    if (this.primitiveType.equals(newValue)) return this;
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
    return primitiveType.equals(another.primitiveType);
  }

  /**
   * Computes a hash code from attributes: {@code primitiveType}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + primitiveType.hashCode();
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
        .add("primitiveType", primitiveType)
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
    @Nullable PrimitiveType primitiveType;
    @JsonProperty("primitiveType")
    public void setPrimitiveType(PrimitiveType primitiveType) {
      this.primitiveType = primitiveType;
    }
    @Override
    public PrimitiveType getPrimitiveType() { throw new UnsupportedOperationException(); }
    @Override
    public String getQualifier() { throw new UnsupportedOperationException(); }
    @Override
    public String getSimpleName() { throw new UnsupportedOperationException(); }
    @Override
    public List<String> getMetaTags() { throw new UnsupportedOperationException(); }
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
    if (json.primitiveType != null) {
      builder.primitiveType(json.primitiveType);
    }
    return builder.build();
  }

  @SuppressWarnings("Immutable")
  private transient volatile long lazyInitBitmap;

  private static final long QUALIFIER_LAZY_INIT_BIT = 0x1L;

  @SuppressWarnings("Immutable")
  private transient String qualifier;

  /**
   * {@inheritDoc}
   * <p>
   * Returns a lazily initialized value of the {@link IPrimitiveValueType#getQualifier() qualifier} attribute.
   * Initialized once and only once and stored for subsequent access with proper synchronization.
   * In case of any exception or error thrown by the lazy value initializer,
   * the result will not be memoised (i.e. remembered) and on next call computation
   * will be attempted again.
   * @return A lazily initialized value of the {@code qualifier} attribute
   */
  @Override
  public String getQualifier() {
    if ((lazyInitBitmap & QUALIFIER_LAZY_INIT_BIT) == 0) {
      synchronized (this) {
        if ((lazyInitBitmap & QUALIFIER_LAZY_INIT_BIT) == 0) {
          this.qualifier = Objects.requireNonNull(IPrimitiveValueType.super.getQualifier(), "qualifier");
          lazyInitBitmap |= QUALIFIER_LAZY_INIT_BIT;
        }
      }
    }
    return qualifier;
  }

  private static final long SIMPLE_NAME_LAZY_INIT_BIT = 0x2L;

  @SuppressWarnings("Immutable")
  private transient String simpleName;

  /**
   * {@inheritDoc}
   * <p>
   * Returns a lazily initialized value of the {@link IPrimitiveValueType#getSimpleName() simpleName} attribute.
   * Initialized once and only once and stored for subsequent access with proper synchronization.
   * In case of any exception or error thrown by the lazy value initializer,
   * the result will not be memoised (i.e. remembered) and on next call computation
   * will be attempted again.
   * @return A lazily initialized value of the {@code simpleName} attribute
   */
  @Override
  public String getSimpleName() {
    if ((lazyInitBitmap & SIMPLE_NAME_LAZY_INIT_BIT) == 0) {
      synchronized (this) {
        if ((lazyInitBitmap & SIMPLE_NAME_LAZY_INIT_BIT) == 0) {
          this.simpleName = Objects.requireNonNull(IPrimitiveValueType.super.getSimpleName(), "simpleName");
          lazyInitBitmap |= SIMPLE_NAME_LAZY_INIT_BIT;
        }
      }
    }
    return simpleName;
  }

  private static final long META_TAGS_LAZY_INIT_BIT = 0x4L;

  @SuppressWarnings("Immutable")
  private transient List<String> metaTags;

  /**
   * {@inheritDoc}
   * <p>
   * Returns a lazily initialized value of the {@link IPrimitiveValueType#getMetaTags() metaTags} attribute.
   * Initialized once and only once and stored for subsequent access with proper synchronization.
   * In case of any exception or error thrown by the lazy value initializer,
   * the result will not be memoised (i.e. remembered) and on next call computation
   * will be attempted again.
   * @return A lazily initialized value of the {@code metaTags} attribute
   */
  @Override
  public List<String> getMetaTags() {
    if ((lazyInitBitmap & META_TAGS_LAZY_INIT_BIT) == 0) {
      synchronized (this) {
        if ((lazyInitBitmap & META_TAGS_LAZY_INIT_BIT) == 0) {
          this.metaTags = Objects.requireNonNull(IPrimitiveValueType.super.getMetaTags(), "metaTags");
          lazyInitBitmap |= META_TAGS_LAZY_INIT_BIT;
        }
      }
    }
    return metaTags;
  }

  private static final long TYPE_CLASS_LAZY_INIT_BIT = 0x8L;

  @SuppressWarnings("Immutable")
  private transient String typeClass;

  /**
   * {@inheritDoc}
   * <p>
   * Returns a lazily initialized value of the {@link IPrimitiveValueType#getTypeClass() typeClass} attribute.
   * Initialized once and only once and stored for subsequent access with proper synchronization.
   * In case of any exception or error thrown by the lazy value initializer,
   * the result will not be memoised (i.e. remembered) and on next call computation
   * will be attempted again.
   * @return A lazily initialized value of the {@code typeClass} attribute
   */
  @Override
  public String getTypeClass() {
    if ((lazyInitBitmap & TYPE_CLASS_LAZY_INIT_BIT) == 0) {
      synchronized (this) {
        if ((lazyInitBitmap & TYPE_CLASS_LAZY_INIT_BIT) == 0) {
          this.typeClass = Objects.requireNonNull(IPrimitiveValueType.super.getTypeClass(), "typeClass");
          lazyInitBitmap |= TYPE_CLASS_LAZY_INIT_BIT;
        }
      }
    }
    return typeClass;
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
   *    .primitiveType(de.upb.sede.core.PrimitiveType) // required {@link IPrimitiveValueType#getPrimitiveType() primitiveType}
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
    private static final long INIT_BIT_PRIMITIVE_TYPE = 0x1L;
    private long initBits = 0x1L;

    private @Nullable PrimitiveType primitiveType;

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
      if (instance.primitiveTypeIsSet()) {
        primitiveType(instance.getPrimitiveType());
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IPrimitiveValueType} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IPrimitiveValueType instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutablePrimitiveValueType) {
        return from((MutablePrimitiveValueType) instance);
      }
      primitiveType(instance.getPrimitiveType());
      return this;
    }

    /**
     * Initializes the value for the {@link IPrimitiveValueType#getPrimitiveType() primitiveType} attribute.
     * @param primitiveType The value for primitiveType 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("primitiveType")
    public final Builder primitiveType(PrimitiveType primitiveType) {
      this.primitiveType = Objects.requireNonNull(primitiveType, "primitiveType");
      initBits &= ~INIT_BIT_PRIMITIVE_TYPE;
      return this;
    }

    /**
     * Builds a new {@link PrimitiveValueType PrimitiveValueType}.
     * @return An immutable instance of PrimitiveValueType
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public PrimitiveValueType build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new PrimitiveValueType(primitiveType);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_PRIMITIVE_TYPE) != 0) attributes.add("primitiveType");
      return "Cannot build PrimitiveValueType, some of required attributes are not set " + attributes;
    }
  }
}
