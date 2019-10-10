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
  private final TypeClass referencedType;
  private final String typeClass;

  private RefType(RefType.Builder builder) {
    this.referencedType = builder.referencedType;
    this.typeClass = builder.typeClass != null
        ? builder.typeClass
        : Objects.requireNonNull(IRefType.super.getTypeClass(), "typeClass");
  }

  private RefType(TypeClass referencedType, String typeClass) {
    this.referencedType = referencedType;
    this.typeClass = typeClass;
  }

  /**
   * @return The value of the {@code referencedType} attribute
   */
  @JsonProperty("referencedType")
  @Override
  public TypeClass getReferencedType() {
    return referencedType;
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
   * Copy the current immutable object by setting a value for the {@link IRefType#getReferencedType() referencedType} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for referencedType
   * @return A modified copy of the {@code this} object
   */
  public final RefType withReferencedType(TypeClass value) {
    if (this.referencedType == value) return this;
    TypeClass newValue = Objects.requireNonNull(value, "referencedType");
    return new RefType(newValue, this.typeClass);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IRefType#getTypeClass() typeClass} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for typeClass
   * @return A modified copy of the {@code this} object
   */
  public final RefType withTypeClass(String value) {
    String newValue = Objects.requireNonNull(value, "typeClass");
    if (this.typeClass.equals(newValue)) return this;
    return new RefType(this.referencedType, newValue);
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
    return referencedType.equals(another.referencedType)
        && typeClass.equals(another.typeClass);
  }

  /**
   * Computes a hash code from attributes: {@code referencedType}, {@code typeClass}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + referencedType.hashCode();
    h += (h << 5) + typeClass.hashCode();
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
        .add("referencedType", referencedType)
        .add("typeClass", typeClass)
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
    @Nullable TypeClass referencedType;
    @Nullable String typeClass;
    @JsonProperty("referencedType")
    public void setReferencedType(TypeClass referencedType) {
      this.referencedType = referencedType;
    }
    @JsonProperty("typeClass")
    public void setTypeClass(String typeClass) {
      this.typeClass = typeClass;
    }
    @Override
    public TypeClass getReferencedType() { throw new UnsupportedOperationException(); }
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
    if (json.referencedType != null) {
      builder.referencedType(json.referencedType);
    }
    if (json.typeClass != null) {
      builder.typeClass(json.typeClass);
    }
    return builder.build();
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
   *    .referencedType(de.upb.sede.composition.graphs.types.TypeClass) // required {@link IRefType#getReferencedType() referencedType}
   *    .typeClass(String) // optional {@link IRefType#getTypeClass() typeClass}
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
    private static final long INIT_BIT_REFERENCED_TYPE = 0x1L;
    private long initBits = 0x1L;

    private @Nullable TypeClass referencedType;
    private @Nullable String typeClass;

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
      if (instance.referencedTypeIsSet()) {
        referencedType(instance.getReferencedType());
      }
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
     * Fill a builder with attribute values from the provided {@code de.upb.sede.composition.graphs.types.IRefType} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IRefType instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof MutableRefType) {
        from((MutableRefType) object);
        return;
      }
      if (object instanceof TypeClass) {
        TypeClass instance = (TypeClass) object;
        typeClass(instance.getTypeClass());
      }
      if (object instanceof IRefType) {
        IRefType instance = (IRefType) object;
        referencedType(instance.getReferencedType());
      }
    }

    /**
     * Initializes the value for the {@link IRefType#getReferencedType() referencedType} attribute.
     * @param referencedType The value for referencedType 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("referencedType")
    public final Builder referencedType(TypeClass referencedType) {
      this.referencedType = Objects.requireNonNull(referencedType, "referencedType");
      initBits &= ~INIT_BIT_REFERENCED_TYPE;
      return this;
    }

    /**
     * Initializes the value for the {@link IRefType#getTypeClass() typeClass} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IRefType#getTypeClass() typeClass}.</em>
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
     * Builds a new {@link RefType RefType}.
     * @return An immutable instance of RefType
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public RefType build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new RefType(this);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_REFERENCED_TYPE) != 0) attributes.add("referencedType");
      return "Cannot build RefType, some of required attributes are not set " + attributes;
    }
  }
}
