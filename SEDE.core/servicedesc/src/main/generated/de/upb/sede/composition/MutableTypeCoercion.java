package de.upb.sede.composition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link ITypeCoercion ITypeCoercion} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableTypeCoercion is not thread-safe</em>
 * @see TypeCoercion
 */
@Generated(from = "ITypeCoercion", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "ITypeCoercion"})
@NotThreadSafe
public final class MutableTypeCoercion implements ITypeCoercion {
  private static final long INIT_BIT_SOURCE_TYPE = 0x1L;
  private static final long INIT_BIT_RESULT_TYPE = 0x2L;
  private long initBits = 0x3L;

  private @Nullable String constant;
  private String sourceType;
  private @Nullable String semanticType;
  private String resultType;

  private MutableTypeCoercion() {}

  /**
   * Construct a modifiable instance of {@code ITypeCoercion}.
   * @return A new modifiable instance
   */
  public static MutableTypeCoercion create() {
    return new MutableTypeCoercion();
  }

  /**
   * @return value of {@code constant} attribute, may be {@code null}
   */
  @JsonProperty("constant")
  @Override
  public final @Nullable String getConstant() {
    return constant;
  }

  /**
   * @return value of {@code sourceType} attribute
   */
  @JsonProperty("sourceType")
  @Override
  public final String getSourceType() {
    if (!sourceTypeIsSet()) {
      checkRequiredAttributes();
    }
    return sourceType;
  }

  /**
   * @return value of {@code semanticType} attribute, may be {@code null}
   */
  @JsonProperty("semanticType")
  @Override
  public final @Nullable String getSemanticType() {
    return semanticType;
  }

  /**
   * @return value of {@code resultType} attribute
   */
  @JsonProperty("resultType")
  @Override
  public final String getResultType() {
    if (!resultTypeIsSet()) {
      checkRequiredAttributes();
    }
    return resultType;
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableTypeCoercion clear() {
    initBits = 0x3L;
    constant = null;
    sourceType = null;
    semanticType = null;
    resultType = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link ITypeCoercion} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableTypeCoercion from(ITypeCoercion instance) {
    Objects.requireNonNull(instance, "instance");
    if (instance instanceof MutableTypeCoercion) {
      from((MutableTypeCoercion) instance);
      return this;
    }
    @Nullable String constantValue = instance.getConstant();
    if (constantValue != null) {
      setConstant(constantValue);
    }
    setSourceType(instance.getSourceType());
    @Nullable String semanticTypeValue = instance.getSemanticType();
    if (semanticTypeValue != null) {
      setSemanticType(semanticTypeValue);
    }
    setResultType(instance.getResultType());
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link ITypeCoercion} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableTypeCoercion from(MutableTypeCoercion instance) {
    Objects.requireNonNull(instance, "instance");
    @Nullable String constantValue = instance.getConstant();
    if (constantValue != null) {
      setConstant(constantValue);
    }
    if (instance.sourceTypeIsSet()) {
      setSourceType(instance.getSourceType());
    }
    @Nullable String semanticTypeValue = instance.getSemanticType();
    if (semanticTypeValue != null) {
      setSemanticType(semanticTypeValue);
    }
    if (instance.resultTypeIsSet()) {
      setResultType(instance.getResultType());
    }
    return this;
  }

  /**
   * Assigns a value to the {@link ITypeCoercion#getConstant() constant} attribute.
   * @param constant The value for constant, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableTypeCoercion setConstant(@Nullable String constant) {
    this.constant = constant;
    return this;
  }

  /**
   * Assigns a value to the {@link ITypeCoercion#getSourceType() sourceType} attribute.
   * @param sourceType The value for sourceType
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableTypeCoercion setSourceType(String sourceType) {
    this.sourceType = Objects.requireNonNull(sourceType, "sourceType");
    initBits &= ~INIT_BIT_SOURCE_TYPE;
    return this;
  }

  /**
   * Assigns a value to the {@link ITypeCoercion#getSemanticType() semanticType} attribute.
   * @param semanticType The value for semanticType, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableTypeCoercion setSemanticType(@Nullable String semanticType) {
    this.semanticType = semanticType;
    return this;
  }

  /**
   * Assigns a value to the {@link ITypeCoercion#getResultType() resultType} attribute.
   * @param resultType The value for resultType
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableTypeCoercion setResultType(String resultType) {
    this.resultType = Objects.requireNonNull(resultType, "resultType");
    initBits &= ~INIT_BIT_RESULT_TYPE;
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link ITypeCoercion#getSourceType() sourceType} is set.
   * @return {@code true} if set
   */
  public final boolean sourceTypeIsSet() {
    return (initBits & INIT_BIT_SOURCE_TYPE) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link ITypeCoercion#getResultType() resultType} is set.
   * @return {@code true} if set
   */
  public final boolean resultTypeIsSet() {
    return (initBits & INIT_BIT_RESULT_TYPE) == 0;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableTypeCoercion unsetSourceType() {
    initBits |= INIT_BIT_SOURCE_TYPE;
    sourceType = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableTypeCoercion unsetResultType() {
    initBits |= INIT_BIT_RESULT_TYPE;
    resultType = null;
    return this;
  }

  /**
   * Returns {@code true} if all required attributes are set, indicating that the object is initialized.
   * @return {@code true} if set
   */
  public final boolean isInitialized() {
    return initBits == 0;
  }

  private void checkRequiredAttributes() {
    if (!isInitialized()) {
      throw new IllegalStateException(formatRequiredAttributesMessage());
    }
  }

  private String formatRequiredAttributesMessage() {
    List<String> attributes = new ArrayList<>();
    if (!sourceTypeIsSet()) attributes.add("sourceType");
    if (!resultTypeIsSet()) attributes.add("resultType");
    return "TypeCoercion is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link TypeCoercion TypeCoercion}.
   * @return An immutable instance of TypeCoercion
   */
  public final TypeCoercion toImmutable() {
    checkRequiredAttributes();
    return TypeCoercion.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableTypeCoercion} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableTypeCoercion)) return false;
    MutableTypeCoercion other = (MutableTypeCoercion) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableTypeCoercion another) {
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
    int h = 5381;
    h += (h << 5) + Objects.hashCode(constant);
    h += (h << 5) + sourceType.hashCode();
    h += (h << 5) + Objects.hashCode(semanticType);
    h += (h << 5) + resultType.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code ITypeCoercion}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableTypeCoercion")
        .add("constant", getConstant())
        .add("sourceType", sourceTypeIsSet() ? getSourceType() : "?")
        .add("semanticType", getSemanticType())
        .add("resultType", resultTypeIsSet() ? getResultType() : "?")
        .toString();
  }
}
