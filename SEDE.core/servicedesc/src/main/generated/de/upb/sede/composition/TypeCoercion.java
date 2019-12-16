package de.upb.sede.composition;

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
 * Immutable implementation of {@link ITypeCoercion}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code TypeCoercion.builder()}.
 */
@Generated(from = "ITypeCoercion", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class TypeCoercion implements ITypeCoercion {
  private final @Nullable String constant;
  private final String sourceType;
  private final @Nullable String semanticType;
  private final String resultType;

  private TypeCoercion(
      @Nullable String constant,
      String sourceType,
      @Nullable String semanticType,
      String resultType) {
    this.constant = constant;
    this.sourceType = sourceType;
    this.semanticType = semanticType;
    this.resultType = resultType;
  }

  /**
   * @return The value of the {@code constant} attribute
   */
  @JsonProperty("constant")
  @Override
  public @Nullable String getConstant() {
    return constant;
  }

  /**
   * @return The value of the {@code sourceType} attribute
   */
  @JsonProperty("sourceType")
  @Override
  public String getSourceType() {
    return sourceType;
  }

  /**
   * @return The value of the {@code semanticType} attribute
   */
  @JsonProperty("semanticType")
  @Override
  public @Nullable String getSemanticType() {
    return semanticType;
  }

  /**
   * @return The value of the {@code resultType} attribute
   */
  @JsonProperty("resultType")
  @Override
  public String getResultType() {
    return resultType;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ITypeCoercion#getConstant() constant} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for constant (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final TypeCoercion withConstant(@Nullable String value) {
    if (Objects.equals(this.constant, value)) return this;
    return new TypeCoercion(value, this.sourceType, this.semanticType, this.resultType);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ITypeCoercion#getSourceType() sourceType} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for sourceType
   * @return A modified copy of the {@code this} object
   */
  public final TypeCoercion withSourceType(String value) {
    String newValue = Objects.requireNonNull(value, "sourceType");
    if (this.sourceType.equals(newValue)) return this;
    return new TypeCoercion(this.constant, newValue, this.semanticType, this.resultType);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ITypeCoercion#getSemanticType() semanticType} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for semanticType (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final TypeCoercion withSemanticType(@Nullable String value) {
    if (Objects.equals(this.semanticType, value)) return this;
    return new TypeCoercion(this.constant, this.sourceType, value, this.resultType);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ITypeCoercion#getResultType() resultType} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for resultType
   * @return A modified copy of the {@code this} object
   */
  public final TypeCoercion withResultType(String value) {
    String newValue = Objects.requireNonNull(value, "resultType");
    if (this.resultType.equals(newValue)) return this;
    return new TypeCoercion(this.constant, this.sourceType, this.semanticType, newValue);
  }

  /**
   * This instance is equal to all instances of {@code TypeCoercion} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof TypeCoercion
        && equalTo((TypeCoercion) another);
  }

  private boolean equalTo(TypeCoercion another) {
    return Objects.equals(constant, another.constant)
        && sourceType.equals(another.sourceType)
        && Objects.equals(semanticType, another.semanticType)
        && resultType.equals(another.resultType);
  }

  /**
   * Computes a hash code from attributes: {@code constant}, {@code sourceType}, {@code semanticType}, {@code resultType}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + Objects.hashCode(constant);
    h += (h << 5) + sourceType.hashCode();
    h += (h << 5) + Objects.hashCode(semanticType);
    h += (h << 5) + resultType.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code TypeCoercion} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("TypeCoercion")
        .omitNullValues()
        .add("constant", constant)
        .add("sourceType", sourceType)
        .add("semanticType", semanticType)
        .add("resultType", resultType)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "ITypeCoercion", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements ITypeCoercion {
    @Nullable String constant;
    @Nullable String sourceType;
    @Nullable String semanticType;
    @Nullable String resultType;
    @JsonProperty("constant")
    public void setConstant(@Nullable String constant) {
      this.constant = constant;
    }
    @JsonProperty("sourceType")
    public void setSourceType(String sourceType) {
      this.sourceType = sourceType;
    }
    @JsonProperty("semanticType")
    public void setSemanticType(@Nullable String semanticType) {
      this.semanticType = semanticType;
    }
    @JsonProperty("resultType")
    public void setResultType(String resultType) {
      this.resultType = resultType;
    }
    @Override
    public String getConstant() { throw new UnsupportedOperationException(); }
    @Override
    public String getSourceType() { throw new UnsupportedOperationException(); }
    @Override
    public String getSemanticType() { throw new UnsupportedOperationException(); }
    @Override
    public String getResultType() { throw new UnsupportedOperationException(); }
    @Override
    public boolean hasConstant() { throw new UnsupportedOperationException(); }
    @Override
    public boolean hasTypeConversion() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static TypeCoercion fromJson(Json json) {
    TypeCoercion.Builder builder = TypeCoercion.builder();
    if (json.constant != null) {
      builder.constant(json.constant);
    }
    if (json.sourceType != null) {
      builder.sourceType(json.sourceType);
    }
    if (json.semanticType != null) {
      builder.semanticType(json.semanticType);
    }
    if (json.resultType != null) {
      builder.resultType(json.resultType);
    }
    return builder.build();
  }

  @SuppressWarnings("Immutable")
  private transient volatile long lazyInitBitmap;

  private static final long HAS_CONSTANT_LAZY_INIT_BIT = 0x1L;

  @SuppressWarnings("Immutable")
  private transient boolean hasConstant;

  /**
   * {@inheritDoc}
   * <p>
   * Returns a lazily initialized value of the {@link ITypeCoercion#hasConstant() hasConstant} attribute.
   * Initialized once and only once and stored for subsequent access with proper synchronization.
   * In case of any exception or error thrown by the lazy value initializer,
   * the result will not be memoised (i.e. remembered) and on next call computation
   * will be attempted again.
   * @return A lazily initialized value of the {@code hasConstant} attribute
   */
  @Override
  public boolean hasConstant() {
    if ((lazyInitBitmap & HAS_CONSTANT_LAZY_INIT_BIT) == 0) {
      synchronized (this) {
        if ((lazyInitBitmap & HAS_CONSTANT_LAZY_INIT_BIT) == 0) {
          this.hasConstant = ITypeCoercion.super.hasConstant();
          lazyInitBitmap |= HAS_CONSTANT_LAZY_INIT_BIT;
        }
      }
    }
    return hasConstant;
  }

  private static final long HAS_TYPE_CONVERSION_LAZY_INIT_BIT = 0x2L;

  @SuppressWarnings("Immutable")
  private transient boolean hasTypeConversion;

  /**
   * {@inheritDoc}
   * <p>
   * Returns a lazily initialized value of the {@link ITypeCoercion#hasTypeConversion() hasTypeConversion} attribute.
   * Initialized once and only once and stored for subsequent access with proper synchronization.
   * In case of any exception or error thrown by the lazy value initializer,
   * the result will not be memoised (i.e. remembered) and on next call computation
   * will be attempted again.
   * @return A lazily initialized value of the {@code hasTypeConversion} attribute
   */
  @Override
  public boolean hasTypeConversion() {
    if ((lazyInitBitmap & HAS_TYPE_CONVERSION_LAZY_INIT_BIT) == 0) {
      synchronized (this) {
        if ((lazyInitBitmap & HAS_TYPE_CONVERSION_LAZY_INIT_BIT) == 0) {
          this.hasTypeConversion = ITypeCoercion.super.hasTypeConversion();
          lazyInitBitmap |= HAS_TYPE_CONVERSION_LAZY_INIT_BIT;
        }
      }
    }
    return hasTypeConversion;
  }

  /**
   * Creates an immutable copy of a {@link ITypeCoercion} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable TypeCoercion instance
   */
  public static TypeCoercion copyOf(ITypeCoercion instance) {
    if (instance instanceof TypeCoercion) {
      return (TypeCoercion) instance;
    }
    return TypeCoercion.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link TypeCoercion TypeCoercion}.
   * <pre>
   * TypeCoercion.builder()
   *    .constant(String | null) // nullable {@link ITypeCoercion#getConstant() constant}
   *    .sourceType(String) // required {@link ITypeCoercion#getSourceType() sourceType}
   *    .semanticType(String | null) // nullable {@link ITypeCoercion#getSemanticType() semanticType}
   *    .resultType(String) // required {@link ITypeCoercion#getResultType() resultType}
   *    .build();
   * </pre>
   * @return A new TypeCoercion builder
   */
  public static TypeCoercion.Builder builder() {
    return new TypeCoercion.Builder();
  }

  /**
   * Builds instances of type {@link TypeCoercion TypeCoercion}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "ITypeCoercion", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_SOURCE_TYPE = 0x1L;
    private static final long INIT_BIT_RESULT_TYPE = 0x2L;
    private long initBits = 0x3L;

    private @Nullable String constant;
    private @Nullable String sourceType;
    private @Nullable String semanticType;
    private @Nullable String resultType;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableTypeCoercion} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableTypeCoercion instance) {
      Objects.requireNonNull(instance, "instance");
      @Nullable String constantValue = instance.getConstant();
      if (constantValue != null) {
        constant(constantValue);
      }
      if (instance.sourceTypeIsSet()) {
        sourceType(instance.getSourceType());
      }
      @Nullable String semanticTypeValue = instance.getSemanticType();
      if (semanticTypeValue != null) {
        semanticType(semanticTypeValue);
      }
      if (instance.resultTypeIsSet()) {
        resultType(instance.getResultType());
      }
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code ITypeCoercion} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(ITypeCoercion instance) {
      Objects.requireNonNull(instance, "instance");
      if (instance instanceof MutableTypeCoercion) {
        return from((MutableTypeCoercion) instance);
      }
      @Nullable String constantValue = instance.getConstant();
      if (constantValue != null) {
        constant(constantValue);
      }
      sourceType(instance.getSourceType());
      @Nullable String semanticTypeValue = instance.getSemanticType();
      if (semanticTypeValue != null) {
        semanticType(semanticTypeValue);
      }
      resultType(instance.getResultType());
      return this;
    }

    /**
     * Initializes the value for the {@link ITypeCoercion#getConstant() constant} attribute.
     * @param constant The value for constant (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("constant")
    public final Builder constant(@Nullable String constant) {
      this.constant = constant;
      return this;
    }

    /**
     * Initializes the value for the {@link ITypeCoercion#getSourceType() sourceType} attribute.
     * @param sourceType The value for sourceType 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("sourceType")
    public final Builder sourceType(String sourceType) {
      this.sourceType = Objects.requireNonNull(sourceType, "sourceType");
      initBits &= ~INIT_BIT_SOURCE_TYPE;
      return this;
    }

    /**
     * Initializes the value for the {@link ITypeCoercion#getSemanticType() semanticType} attribute.
     * @param semanticType The value for semanticType (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("semanticType")
    public final Builder semanticType(@Nullable String semanticType) {
      this.semanticType = semanticType;
      return this;
    }

    /**
     * Initializes the value for the {@link ITypeCoercion#getResultType() resultType} attribute.
     * @param resultType The value for resultType 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("resultType")
    public final Builder resultType(String resultType) {
      this.resultType = Objects.requireNonNull(resultType, "resultType");
      initBits &= ~INIT_BIT_RESULT_TYPE;
      return this;
    }

    /**
     * Builds a new {@link TypeCoercion TypeCoercion}.
     * @return An immutable instance of TypeCoercion
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public TypeCoercion build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new TypeCoercion(constant, sourceType, semanticType, resultType);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_SOURCE_TYPE) != 0) attributes.add("sourceType");
      if ((initBits & INIT_BIT_RESULT_TYPE) != 0) attributes.add("resultType");
      return "Cannot build TypeCoercion, some of required attributes are not set " + attributes;
    }
  }
}
