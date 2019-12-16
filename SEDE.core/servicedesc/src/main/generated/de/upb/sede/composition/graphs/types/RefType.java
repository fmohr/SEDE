package de.upb.sede.composition.graphs.types;

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
 * Immutable implementation of {@link IRefType}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code RefType.builder()}.
 */
@Generated(from = "IRefType", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class RefType implements IRefType {
  private final ValueTypeClass typeOfRef;

  private RefType(ValueTypeClass typeOfRef) {
    this.typeOfRef = typeOfRef;
  }

  /**
   * @return The value of the {@code typeOfRef} attribute
   */
  @JsonProperty("typeOfRef")
  @Override
  public ValueTypeClass getTypeOfRef() {
    return typeOfRef;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IRefType#getTypeOfRef() typeOfRef} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for typeOfRef
   * @return A modified copy of the {@code this} object
   */
  public final RefType withTypeOfRef(ValueTypeClass value) {
    if (this.typeOfRef == value) return this;
    ValueTypeClass newValue = Objects.requireNonNull(value, "typeOfRef");
    return new RefType(newValue);
  }

  /**
   * This instance is equal to all instances of {@code RefType} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof RefType
        && equalTo((RefType) another);
  }

  private boolean equalTo(RefType another) {
    return typeOfRef.equals(another.typeOfRef);
  }

  /**
   * Computes a hash code from attributes: {@code typeOfRef}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + typeOfRef.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code RefType} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("RefType")
        .omitNullValues()
        .add("typeOfRef", typeOfRef)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IRefType", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IRefType {
    @Nullable ValueTypeClass typeOfRef;
    @JsonProperty("typeOfRef")
    public void setTypeOfRef(ValueTypeClass typeOfRef) {
      this.typeOfRef = typeOfRef;
    }
    @Override
    public ValueTypeClass getTypeOfRef() { throw new UnsupportedOperationException(); }
    @Override
    public String getTypeQualifier() { throw new UnsupportedOperationException(); }
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
  static RefType fromJson(Json json) {
    RefType.Builder builder = RefType.builder();
    if (json.typeOfRef != null) {
      builder.typeOfRef(json.typeOfRef);
    }
    return builder.build();
  }

  @SuppressWarnings("Immutable")
  private transient volatile long lazyInitBitmap;

  private static final long TYPE_QUALIFIER_LAZY_INIT_BIT = 0x1L;

  @SuppressWarnings("Immutable")
  private transient String typeQualifier;

  /**
   * {@inheritDoc}
   * <p>
   * Returns a lazily initialized value of the {@link IRefType#getTypeQualifier() typeQualifier} attribute.
   * Initialized once and only once and stored for subsequent access with proper synchronization.
   * In case of any exception or error thrown by the lazy value initializer,
   * the result will not be memoised (i.e. remembered) and on next call computation
   * will be attempted again.
   * @return A lazily initialized value of the {@code typeQualifier} attribute
   */
  @Override
  public String getTypeQualifier() {
    if ((lazyInitBitmap & TYPE_QUALIFIER_LAZY_INIT_BIT) == 0) {
      synchronized (this) {
        if ((lazyInitBitmap & TYPE_QUALIFIER_LAZY_INIT_BIT) == 0) {
          this.typeQualifier = Objects.requireNonNull(IRefType.super.getTypeQualifier(), "typeQualifier");
          lazyInitBitmap |= TYPE_QUALIFIER_LAZY_INIT_BIT;
        }
      }
    }
    return typeQualifier;
  }

  private static final long TYPE_CLASS_LAZY_INIT_BIT = 0x2L;

  @SuppressWarnings("Immutable")
  private transient String typeClass;

  /**
   * {@inheritDoc}
   * <p>
   * Returns a lazily initialized value of the {@link IRefType#getTypeClass() typeClass} attribute.
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
          this.typeClass = Objects.requireNonNull(IRefType.super.getTypeClass(), "typeClass");
          lazyInitBitmap |= TYPE_CLASS_LAZY_INIT_BIT;
        }
      }
    }
    return typeClass;
  }

  /**
   * Creates an immutable copy of a {@link IRefType} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable RefType instance
   */
  public static RefType copyOf(IRefType instance) {
    if (instance instanceof RefType) {
      return (RefType) instance;
    }
    return RefType.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link RefType RefType}.
   * <pre>
   * RefType.builder()
   *    .typeOfRef(de.upb.sede.composition.graphs.types.ValueTypeClass) // required {@link IRefType#getTypeOfRef() typeOfRef}
   *    .build();
   * </pre>
   * @return A new RefType builder
   */
  public static RefType.Builder builder() {
    return new RefType.Builder();
  }

  /**
   * Builds instances of type {@link RefType RefType}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IRefType", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_TYPE_OF_REF = 0x1L;
    private long initBits = 0x1L;

    private @Nullable ValueTypeClass typeOfRef;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableRefType} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableRefType instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance.typeOfRefIsSet()) {
        typeOfRef(instance.getTypeOfRef());
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code IRefType} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IRefType instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableRefType) {
        return from((MutableRefType) instance);
      }
      typeOfRef(instance.getTypeOfRef());
      return this;
    }

    /**
     * Initializes the value for the {@link IRefType#getTypeOfRef() typeOfRef} attribute.
     * @param typeOfRef The value for typeOfRef 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("typeOfRef")
    public final Builder typeOfRef(ValueTypeClass typeOfRef) {
      this.typeOfRef = Objects.requireNonNull(typeOfRef, "typeOfRef");
      initBits &= ~INIT_BIT_TYPE_OF_REF;
      return this;
    }

    /**
     * Builds a new {@link RefType RefType}.
     * @return An immutable instance of RefType
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public RefType build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new RefType(typeOfRef);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_TYPE_OF_REF) != 0) attributes.add("typeOfRef");
      return "Cannot build RefType, some of required attributes are not set " + attributes;
    }
  }
}
