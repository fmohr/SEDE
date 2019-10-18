package de.upb.sede.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import de.upb.sede.IQualifiable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * A modifiable implementation of the {@link INumericParameter INumericParameter} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>ModifiableINumericParameter is not thread-safe</em>
 * @see ImmutableINumericParameter
 */
@Generated(from = "INumericParameter", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "INumericParameter"})
@NotThreadSafe
public final class ModifiableINumericParameter implements INumericParameter {
  private static final long INIT_BIT_QUALIFIER = 0x1L;
  private static final long OPT_BIT_IS_INTEGER = 0x1L;
  private long initBits = 0x1L;
  private long optBits;

  private boolean isInteger;
  private @Nullable Double min;
  private @Nullable Double max;
  private @Nullable Integer refineSplits;
  private @Nullable Integer minInterval;
  private @Nullable Double defaultValue;
  private String paramType;
  private String qualifier;
  private final ArrayList<String> metaTags = new ArrayList<String>();
  private String simpleName;

  private ModifiableINumericParameter() {}

  /**
   * Construct a modifiable instance of {@code INumericParameter}.
   * @return A new modifiable instance
   */
  public static ModifiableINumericParameter create() {
    return new ModifiableINumericParameter();
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code isInteger} attribute
   */
  @JsonProperty("isInteger")
  @Override
  public final boolean isInteger() {
    return isIntegerIsSet()
        ? isInteger
        : INumericParameter.super.isInteger();
  }

  /**
   * @return value of {@code min} attribute, may be {@code null}
   */
  @JsonProperty("min")
  @Override
  public final @Nullable Double getMin() {
    return min;
  }

  /**
   * @return value of {@code max} attribute, may be {@code null}
   */
  @JsonProperty("max")
  @Override
  public final @Nullable Double getMax() {
    return max;
  }

  /**
   * @return value of {@code refineSplits} attribute, may be {@code null}
   */
  @JsonProperty("refineSplits")
  @Override
  public final @Nullable Integer getSplitsRefined() {
    return refineSplits;
  }

  /**
   * @return value of {@code minInterval} attribute, may be {@code null}
   */
  @JsonProperty("minInterval")
  @Override
  public final @Nullable Integer getMinInterval() {
    return minInterval;
  }

  /**
   * @return value of {@code defaultValue} attribute, may be {@code null}
   */
  @JsonProperty("defaultValue")
  @Override
  public final @Nullable Double getDefaultValue() {
    return defaultValue;
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code paramType} attribute
   */
  @JsonProperty("paramType")
  @Override
  public final String getParamType() {
    return paramTypeIsSet()
        ? paramType
        : INumericParameter.super.getParamType();
  }

  /**
   * @return value of {@code qualifier} attribute
   */
  @JsonProperty("qualifier")
  @Override
  public final String getQualifier() {
    if (!qualifierIsSet()) {
      checkRequiredAttributes();
    }
    return qualifier;
  }

  /**
   * @return modifiable list {@code metaTags}
   */
  @JsonProperty("metaTags")
  @Override
  public final List<String> getMetaTags() {
    return metaTags;
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code simpleName} attribute
   */
  @JsonProperty("simpleName")
  @Override
  public final String getSimpleName() {
    return simpleNameIsSet()
        ? simpleName
        : INumericParameter.super.getSimpleName();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableINumericParameter clear() {
    initBits = 0x1L;
    optBits = 0;
    isInteger = false;
    min = null;
    max = null;
    refineSplits = null;
    minInterval = null;
    defaultValue = null;
    paramType = null;
    qualifier = null;
    metaTags.clear();
    simpleName = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.param.INumericParameter} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableINumericParameter from(INumericParameter instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.param.IParameter} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableINumericParameter from(IParameter instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.IQualifiable} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableINumericParameter from(IQualifiable instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link INumericParameter} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public ModifiableINumericParameter from(ModifiableINumericParameter instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof ModifiableINumericParameter) {
      ModifiableINumericParameter instance = (ModifiableINumericParameter) object;
      setIsInteger(instance.isInteger());
      @Nullable Double minValue = instance.getMin();
      if (minValue != null) {
        setMin(minValue);
      }
      @Nullable Double maxValue = instance.getMax();
      if (maxValue != null) {
        setMax(maxValue);
      }
      @Nullable Integer refineSplitsValue = instance.getSplitsRefined();
      if (refineSplitsValue != null) {
        setRefineSplits(refineSplitsValue);
      }
      @Nullable Integer minIntervalValue = instance.getMinInterval();
      if (minIntervalValue != null) {
        setMinInterval(minIntervalValue);
      }
      @Nullable Double defaultValueValue = instance.getDefaultValue();
      if (defaultValueValue != null) {
        setDefaultValue(defaultValueValue);
      }
      setParamType(instance.getParamType());
      if (instance.qualifierIsSet()) {
        setQualifier(instance.getQualifier());
      }
      addAllMetaTags(instance.getMetaTags());
      setSimpleName(instance.getSimpleName());
      return;
    }
    if (object instanceof INumericParameter) {
      INumericParameter instance = (INumericParameter) object;
      setIsInteger(instance.isInteger());
      @Nullable Double minValue = instance.getMin();
      if (minValue != null) {
        setMin(minValue);
      }
      @Nullable Double maxValue = instance.getMax();
      if (maxValue != null) {
        setMax(maxValue);
      }
      @Nullable Integer refineSplitsValue = instance.getSplitsRefined();
      if (refineSplitsValue != null) {
        setRefineSplits(refineSplitsValue);
      }
      @Nullable Integer minIntervalValue = instance.getMinInterval();
      if (minIntervalValue != null) {
        setMinInterval(minIntervalValue);
      }
      @Nullable Double defaultValueValue = instance.getDefaultValue();
      if (defaultValueValue != null) {
        setDefaultValue(defaultValueValue);
      }
    }
    if (object instanceof IParameter) {
      IParameter instance = (IParameter) object;
      setParamType(instance.getParamType());
    }
    if (object instanceof IQualifiable) {
      IQualifiable instance = (IQualifiable) object;
      addAllMetaTags(instance.getMetaTags());
      setSimpleName(instance.getSimpleName());
      setQualifier(instance.getQualifier());
    }
  }

  /**
   * Assigns a value to the {@link INumericParameter#isInteger() isInteger} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link INumericParameter#isInteger() isInteger}.</em>
   * @param isInteger The value for isInteger
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableINumericParameter setIsInteger(boolean isInteger) {
    this.isInteger = isInteger;
    optBits |= OPT_BIT_IS_INTEGER;
    return this;
  }

  /**
   * Assigns a value to the {@link INumericParameter#getMin() min} attribute.
   * @param min The value for min, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableINumericParameter setMin(@Nullable Double min) {
    this.min = min;
    return this;
  }

  /**
   * Assigns a value to the {@link INumericParameter#getMax() max} attribute.
   * @param max The value for max, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableINumericParameter setMax(@Nullable Double max) {
    this.max = max;
    return this;
  }

  /**
   * Assigns a value to the {@link INumericParameter#getSplitsRefined() refineSplits} attribute.
   * @param refineSplits The value for refineSplits, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableINumericParameter setRefineSplits(@Nullable Integer refineSplits) {
    this.refineSplits = refineSplits;
    return this;
  }

  /**
   * Assigns a value to the {@link INumericParameter#getMinInterval() minInterval} attribute.
   * @param minInterval The value for minInterval, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableINumericParameter setMinInterval(@Nullable Integer minInterval) {
    this.minInterval = minInterval;
    return this;
  }

  /**
   * Assigns a value to the {@link INumericParameter#getDefaultValue() defaultValue} attribute.
   * @param defaultValue The value for defaultValue, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableINumericParameter setDefaultValue(@Nullable Double defaultValue) {
    this.defaultValue = defaultValue;
    return this;
  }

  /**
   * Assigns a value to the {@link INumericParameter#getParamType() paramType} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link INumericParameter#getParamType() paramType}.</em>
   * @param paramType The value for paramType
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableINumericParameter setParamType(String paramType) {
    this.paramType = Objects.requireNonNull(paramType, "paramType");
    return this;
  }

  /**
   * Assigns a value to the {@link INumericParameter#getQualifier() qualifier} attribute.
   * @param qualifier The value for qualifier
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableINumericParameter setQualifier(String qualifier) {
    this.qualifier = Objects.requireNonNull(qualifier, "qualifier");
    initBits &= ~INIT_BIT_QUALIFIER;
    return this;
  }

  /**
   * Adds one element to {@link INumericParameter#getMetaTags() metaTags} list.
   * @param element The metaTags element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableINumericParameter addMetaTags(String element) {
    Objects.requireNonNull(element, "metaTags element");
    this.metaTags.add(element);
    return this;
  }

  /**
   * Adds elements to {@link INumericParameter#getMetaTags() metaTags} list.
   * @param elements An array of metaTags elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableINumericParameter addMetaTags(String... elements) {
    for (String e : elements) {
      addMetaTags(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link INumericParameter#getMetaTags() metaTags} list.
   * @param elements An iterable of metaTags elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableINumericParameter setMetaTags(Iterable<String> elements) {
    this.metaTags.clear();
    addAllMetaTags(elements);
    return this;
  }

  /**
   * Adds elements to {@link INumericParameter#getMetaTags() metaTags} list.
   * @param elements An iterable of metaTags elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableINumericParameter addAllMetaTags(Iterable<String> elements) {
    for (String e : elements) {
      addMetaTags(e);
    }
    return this;
  }

  /**
   * Assigns a value to the {@link INumericParameter#getSimpleName() simpleName} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link INumericParameter#getSimpleName() simpleName}.</em>
   * @param simpleName The value for simpleName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableINumericParameter setSimpleName(String simpleName) {
    this.simpleName = Objects.requireNonNull(simpleName, "simpleName");
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link INumericParameter#getQualifier() qualifier} is set.
   * @return {@code true} if set
   */
  public final boolean qualifierIsSet() {
    return (initBits & INIT_BIT_QUALIFIER) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link INumericParameter#isInteger() isInteger} is set.
   * @return {@code true} if set
   */
  public final boolean isIntegerIsSet() {
    return (optBits & OPT_BIT_IS_INTEGER) != 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link INumericParameter#getParamType() paramType} is set.
   * @return {@code true} if set
   */
  public final boolean paramTypeIsSet() {
    return paramType != null;
  }

  /**
   * Returns {@code true} if the default attribute {@link INumericParameter#getSimpleName() simpleName} is set.
   * @return {@code true} if set
   */
  public final boolean simpleNameIsSet() {
    return simpleName != null;
  }


  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableINumericParameter unsetQualifier() {
    initBits |= INIT_BIT_QUALIFIER;
    qualifier = null;
    return this;
  }
  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final ModifiableINumericParameter unsetIsInteger() {
    optBits |= 0;
    isInteger = false;
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
    if (!qualifierIsSet()) attributes.add("qualifier");
    return "INumericParameter is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link ImmutableINumericParameter ImmutableINumericParameter}.
   * @return An immutable instance of INumericParameter
   */
  public final ImmutableINumericParameter toImmutable() {
    checkRequiredAttributes();
    return ImmutableINumericParameter.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code ModifiableINumericParameter} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof ModifiableINumericParameter)) return false;
    ModifiableINumericParameter other = (ModifiableINumericParameter) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(ModifiableINumericParameter another) {
    boolean isInteger = isInteger();
    String paramType = getParamType();
    return isInteger == another.isInteger()
        && Objects.equals(min, another.min)
        && Objects.equals(max, another.max)
        && Objects.equals(refineSplits, another.refineSplits)
        && Objects.equals(minInterval, another.minInterval)
        && Objects.equals(defaultValue, another.defaultValue)
        && paramType.equals(another.getParamType())
        && qualifier.equals(another.qualifier);
  }

  /**
   * Computes a hash code from attributes: {@code isInteger}, {@code min}, {@code max}, {@code refineSplits}, {@code minInterval}, {@code defaultValue}, {@code paramType}, {@code qualifier}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    boolean isInteger = isInteger();
    h += (h << 5) + Booleans.hashCode(isInteger);
    h += (h << 5) + Objects.hashCode(min);
    h += (h << 5) + Objects.hashCode(max);
    h += (h << 5) + Objects.hashCode(refineSplits);
    h += (h << 5) + Objects.hashCode(minInterval);
    h += (h << 5) + Objects.hashCode(defaultValue);
    String paramType = getParamType();
    h += (h << 5) + paramType.hashCode();
    h += (h << 5) + qualifier.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code INumericParameter}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ModifiableINumericParameter")
        .add("isInteger", isInteger())
        .add("min", getMin())
        .add("max", getMax())
        .add("refineSplits", getSplitsRefined())
        .add("minInterval", getMinInterval())
        .add("defaultValue", getDefaultValue())
        .add("paramType", getParamType())
        .add("qualifier", qualifierIsSet() ? getQualifier() : "?")
        .toString();
  }
}
