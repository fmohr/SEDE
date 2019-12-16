package de.upb.sede.param;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import de.upb.sede.IQualifiable;
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
 * Immutable implementation of {@link INumericParameter}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code NumericParameter.builder()}.
 */
@Generated(from = "INumericParameter", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class NumericParameter implements INumericParameter {
  private final boolean isInteger;
  private final @Nullable Double min;
  private final @Nullable Double max;
  private final @Nullable Integer splitsRefined;
  private final @Nullable Integer minInterval;
  private final @Nullable Double defaultValue;
  private final boolean isOptional;
  private final String qualifier;
  private final ImmutableList<String> metaTags;
  private final String simpleName;

  private NumericParameter(NumericParameter.Builder builder) {
    this.min = builder.min;
    this.max = builder.max;
    this.splitsRefined = builder.splitsRefined;
    this.minInterval = builder.minInterval;
    this.defaultValue = builder.defaultValue;
    this.qualifier = builder.qualifier;
    this.metaTags = builder.metaTags.build();
    if (builder.isIntegerIsSet()) {
      initShim.isInteger(builder.isInteger);
    }
    if (builder.isOptionalIsSet()) {
      initShim.isOptional(builder.isOptional);
    }
    if (builder.simpleName != null) {
      initShim.simpleName(builder.simpleName);
    }
    this.isInteger = initShim.isInteger();
    this.isOptional = initShim.isOptional();
    this.simpleName = initShim.getSimpleName();
    this.initShim = null;
  }

  private NumericParameter(
      boolean isInteger,
      @Nullable Double min,
      @Nullable Double max,
      @Nullable Integer splitsRefined,
      @Nullable Integer minInterval,
      @Nullable Double defaultValue,
      boolean isOptional,
      String qualifier,
      ImmutableList<String> metaTags,
      String simpleName) {
    this.isInteger = isInteger;
    this.min = min;
    this.max = max;
    this.splitsRefined = splitsRefined;
    this.minInterval = minInterval;
    this.defaultValue = defaultValue;
    this.isOptional = isOptional;
    this.qualifier = qualifier;
    this.metaTags = metaTags;
    this.simpleName = simpleName;
    this.initShim = null;
  }

  private static final byte STAGE_INITIALIZING = -1;
  private static final byte STAGE_UNINITIALIZED = 0;
  private static final byte STAGE_INITIALIZED = 1;
  @SuppressWarnings("Immutable")
  private transient volatile InitShim initShim = new InitShim();

  @Generated(from = "INumericParameter", generator = "Immutables")
  private final class InitShim {
    private byte isIntegerBuildStage = STAGE_UNINITIALIZED;
    private boolean isInteger;

    boolean isInteger() {
      if (isIntegerBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (isIntegerBuildStage == STAGE_UNINITIALIZED) {
        isIntegerBuildStage = STAGE_INITIALIZING;
        this.isInteger = isIntegerInitialize();
        isIntegerBuildStage = STAGE_INITIALIZED;
      }
      return this.isInteger;
    }

    void isInteger(boolean isInteger) {
      this.isInteger = isInteger;
      isIntegerBuildStage = STAGE_INITIALIZED;
    }

    private byte isOptionalBuildStage = STAGE_UNINITIALIZED;
    private boolean isOptional;

    boolean isOptional() {
      if (isOptionalBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (isOptionalBuildStage == STAGE_UNINITIALIZED) {
        isOptionalBuildStage = STAGE_INITIALIZING;
        this.isOptional = isOptionalInitialize();
        isOptionalBuildStage = STAGE_INITIALIZED;
      }
      return this.isOptional;
    }

    void isOptional(boolean isOptional) {
      this.isOptional = isOptional;
      isOptionalBuildStage = STAGE_INITIALIZED;
    }

    private byte simpleNameBuildStage = STAGE_UNINITIALIZED;
    private String simpleName;

    String getSimpleName() {
      if (simpleNameBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (simpleNameBuildStage == STAGE_UNINITIALIZED) {
        simpleNameBuildStage = STAGE_INITIALIZING;
        this.simpleName = Objects.requireNonNull(getSimpleNameInitialize(), "simpleName");
        simpleNameBuildStage = STAGE_INITIALIZED;
      }
      return this.simpleName;
    }

    void simpleName(String simpleName) {
      this.simpleName = simpleName;
      simpleNameBuildStage = STAGE_INITIALIZED;
    }

    private String formatInitCycleMessage() {
      List<String> attributes = new ArrayList<>();
      if (isIntegerBuildStage == STAGE_INITIALIZING) attributes.add("isInteger");
      if (isOptionalBuildStage == STAGE_INITIALIZING) attributes.add("isOptional");
      if (simpleNameBuildStage == STAGE_INITIALIZING) attributes.add("simpleName");
      return "Cannot build NumericParameter, attribute initializers form cycle " + attributes;
    }
  }

  private boolean isIntegerInitialize() {
    return INumericParameter.super.isInteger();
  }

  private boolean isOptionalInitialize() {
    return INumericParameter.super.isOptional();
  }

  private String getSimpleNameInitialize() {
    return INumericParameter.super.getSimpleName();
  }

  /**
   * @return The value of the {@code isInteger} attribute
   */
  @JsonProperty("isInteger")
  @Override
  public boolean isInteger() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.isInteger()
        : this.isInteger;
  }

  /**
   * @return The value of the {@code min} attribute
   */
  @JsonProperty("min")
  @Override
  public @Nullable Double getMin() {
    return min;
  }

  /**
   * @return The value of the {@code max} attribute
   */
  @JsonProperty("max")
  @Override
  public @Nullable Double getMax() {
    return max;
  }

  /**
   * @return The value of the {@code splitsRefined} attribute
   */
  @JsonProperty("splitsRefined")
  @Override
  public @Nullable Integer getSplitsRefined() {
    return splitsRefined;
  }

  /**
   * @return The value of the {@code minInterval} attribute
   */
  @JsonProperty("minInterval")
  @Override
  public @Nullable Integer getMinInterval() {
    return minInterval;
  }

  /**
   * @return The value of the {@code defaultValue} attribute
   */
  @JsonProperty("defaultValue")
  @Override
  public @Nullable Double getDefaultValue() {
    return defaultValue;
  }

  /**
   * @return The value of the {@code isOptional} attribute
   */
  @JsonProperty("isOptional")
  @Override
  public boolean isOptional() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.isOptional()
        : this.isOptional;
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
   * @return The value of the {@code metaTags} attribute
   */
  @JsonProperty("metaTags")
  @Override
  public ImmutableList<String> getMetaTags() {
    return metaTags;
  }

  /**
   * @return The value of the {@code simpleName} attribute
   */
  @JsonProperty("simpleName")
  @Override
  public String getSimpleName() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.getSimpleName()
        : this.simpleName;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link INumericParameter#isInteger() isInteger} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for isInteger
   * @return A modified copy of the {@code this} object
   */
  public final NumericParameter withIsInteger(boolean value) {
    if (this.isInteger == value) return this;
    return new NumericParameter(
        value,
        this.min,
        this.max,
        this.splitsRefined,
        this.minInterval,
        this.defaultValue,
        this.isOptional,
        this.qualifier,
        this.metaTags,
        this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link INumericParameter#getMin() min} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for min (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final NumericParameter withMin(@Nullable Double value) {
    if (Objects.equals(this.min, value)) return this;
    return new NumericParameter(
        this.isInteger,
        value,
        this.max,
        this.splitsRefined,
        this.minInterval,
        this.defaultValue,
        this.isOptional,
        this.qualifier,
        this.metaTags,
        this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link INumericParameter#getMax() max} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for max (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final NumericParameter withMax(@Nullable Double value) {
    if (Objects.equals(this.max, value)) return this;
    return new NumericParameter(
        this.isInteger,
        this.min,
        value,
        this.splitsRefined,
        this.minInterval,
        this.defaultValue,
        this.isOptional,
        this.qualifier,
        this.metaTags,
        this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link INumericParameter#getSplitsRefined() splitsRefined} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for splitsRefined (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final NumericParameter withSplitsRefined(@Nullable Integer value) {
    if (Objects.equals(this.splitsRefined, value)) return this;
    return new NumericParameter(
        this.isInteger,
        this.min,
        this.max,
        value,
        this.minInterval,
        this.defaultValue,
        this.isOptional,
        this.qualifier,
        this.metaTags,
        this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link INumericParameter#getMinInterval() minInterval} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for minInterval (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final NumericParameter withMinInterval(@Nullable Integer value) {
    if (Objects.equals(this.minInterval, value)) return this;
    return new NumericParameter(
        this.isInteger,
        this.min,
        this.max,
        this.splitsRefined,
        value,
        this.defaultValue,
        this.isOptional,
        this.qualifier,
        this.metaTags,
        this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link INumericParameter#getDefaultValue() defaultValue} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for defaultValue (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final NumericParameter withDefaultValue(@Nullable Double value) {
    if (Objects.equals(this.defaultValue, value)) return this;
    return new NumericParameter(
        this.isInteger,
        this.min,
        this.max,
        this.splitsRefined,
        this.minInterval,
        value,
        this.isOptional,
        this.qualifier,
        this.metaTags,
        this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link INumericParameter#isOptional() isOptional} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for isOptional
   * @return A modified copy of the {@code this} object
   */
  public final NumericParameter withIsOptional(boolean value) {
    if (this.isOptional == value) return this;
    return new NumericParameter(
        this.isInteger,
        this.min,
        this.max,
        this.splitsRefined,
        this.minInterval,
        this.defaultValue,
        value,
        this.qualifier,
        this.metaTags,
        this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link INumericParameter#getQualifier() qualifier} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for qualifier
   * @return A modified copy of the {@code this} object
   */
  public final NumericParameter withQualifier(String value) {
    String newValue = Objects.requireNonNull(value, "qualifier");
    if (this.qualifier.equals(newValue)) return this;
    return new NumericParameter(
        this.isInteger,
        this.min,
        this.max,
        this.splitsRefined,
        this.minInterval,
        this.defaultValue,
        this.isOptional,
        newValue,
        this.metaTags,
        this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link INumericParameter#getMetaTags() metaTags}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final NumericParameter withMetaTags(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new NumericParameter(
        this.isInteger,
        this.min,
        this.max,
        this.splitsRefined,
        this.minInterval,
        this.defaultValue,
        this.isOptional,
        this.qualifier,
        newValue,
        this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link INumericParameter#getMetaTags() metaTags}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of metaTags elements to set
   * @return A modified copy of {@code this} object
   */
  public final NumericParameter withMetaTags(Iterable<String> elements) {
    if (this.metaTags == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new NumericParameter(
        this.isInteger,
        this.min,
        this.max,
        this.splitsRefined,
        this.minInterval,
        this.defaultValue,
        this.isOptional,
        this.qualifier,
        newValue,
        this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link INumericParameter#getSimpleName() simpleName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for simpleName
   * @return A modified copy of the {@code this} object
   */
  public final NumericParameter withSimpleName(String value) {
    String newValue = Objects.requireNonNull(value, "simpleName");
    if (this.simpleName.equals(newValue)) return this;
    return new NumericParameter(
        this.isInteger,
        this.min,
        this.max,
        this.splitsRefined,
        this.minInterval,
        this.defaultValue,
        this.isOptional,
        this.qualifier,
        this.metaTags,
        newValue);
  }

  /**
   * This instance is equal to all instances of {@code NumericParameter} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof NumericParameter
        && equalTo((NumericParameter) another);
  }

  private boolean equalTo(NumericParameter another) {
    return isInteger == another.isInteger
        && Objects.equals(min, another.min)
        && Objects.equals(max, another.max)
        && Objects.equals(splitsRefined, another.splitsRefined)
        && Objects.equals(minInterval, another.minInterval)
        && Objects.equals(defaultValue, another.defaultValue)
        && isOptional == another.isOptional
        && qualifier.equals(another.qualifier);
  }

  /**
   * Computes a hash code from attributes: {@code isInteger}, {@code min}, {@code max}, {@code splitsRefined}, {@code minInterval}, {@code defaultValue}, {@code isOptional}, {@code qualifier}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + Booleans.hashCode(isInteger);
    h += (h << 5) + Objects.hashCode(min);
    h += (h << 5) + Objects.hashCode(max);
    h += (h << 5) + Objects.hashCode(splitsRefined);
    h += (h << 5) + Objects.hashCode(minInterval);
    h += (h << 5) + Objects.hashCode(defaultValue);
    h += (h << 5) + Booleans.hashCode(isOptional);
    h += (h << 5) + qualifier.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code NumericParameter} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("NumericParameter")
        .omitNullValues()
        .add("isInteger", isInteger)
        .add("min", min)
        .add("max", max)
        .add("splitsRefined", splitsRefined)
        .add("minInterval", minInterval)
        .add("defaultValue", defaultValue)
        .add("isOptional", isOptional)
        .add("qualifier", qualifier)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "INumericParameter", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements INumericParameter {
    boolean isInteger;
    boolean isIntegerIsSet;
    @Nullable Double min;
    @Nullable Double max;
    @Nullable Integer splitsRefined;
    @Nullable Integer minInterval;
    @Nullable Double defaultValue;
    boolean isOptional;
    boolean isOptionalIsSet;
    @Nullable String qualifier;
    @Nullable List<String> metaTags = ImmutableList.of();
    @Nullable String simpleName;
    @JsonProperty("isInteger")
    public void setIsInteger(boolean isInteger) {
      this.isInteger = isInteger;
      this.isIntegerIsSet = true;
    }
    @JsonProperty("min")
    public void setMin(@Nullable Double min) {
      this.min = min;
    }
    @JsonProperty("max")
    public void setMax(@Nullable Double max) {
      this.max = max;
    }
    @JsonProperty("splitsRefined")
    public void setSplitsRefined(@Nullable Integer splitsRefined) {
      this.splitsRefined = splitsRefined;
    }
    @JsonProperty("minInterval")
    public void setMinInterval(@Nullable Integer minInterval) {
      this.minInterval = minInterval;
    }
    @JsonProperty("defaultValue")
    public void setDefaultValue(@Nullable Double defaultValue) {
      this.defaultValue = defaultValue;
    }
    @JsonProperty("isOptional")
    public void setIsOptional(boolean isOptional) {
      this.isOptional = isOptional;
      this.isOptionalIsSet = true;
    }
    @JsonProperty("qualifier")
    public void setQualifier(String qualifier) {
      this.qualifier = qualifier;
    }
    @JsonProperty("metaTags")
    public void setMetaTags(List<String> metaTags) {
      this.metaTags = metaTags;
    }
    @JsonProperty("simpleName")
    public void setSimpleName(String simpleName) {
      this.simpleName = simpleName;
    }
    @Override
    public boolean isInteger() { throw new UnsupportedOperationException(); }
    @Override
    public Double getMin() { throw new UnsupportedOperationException(); }
    @Override
    public Double getMax() { throw new UnsupportedOperationException(); }
    @Override
    public Integer getSplitsRefined() { throw new UnsupportedOperationException(); }
    @Override
    public Integer getMinInterval() { throw new UnsupportedOperationException(); }
    @Override
    public Double getDefaultValue() { throw new UnsupportedOperationException(); }
    @Override
    public boolean isOptional() { throw new UnsupportedOperationException(); }
    @Override
    public String getQualifier() { throw new UnsupportedOperationException(); }
    @Override
    public List<String> getMetaTags() { throw new UnsupportedOperationException(); }
    @Override
    public String getSimpleName() { throw new UnsupportedOperationException(); }
    @Override
    public String getParamType() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static NumericParameter fromJson(Json json) {
    NumericParameter.Builder builder = NumericParameter.builder();
    if (json.isIntegerIsSet) {
      builder.isInteger(json.isInteger);
    }
    if (json.min != null) {
      builder.min(json.min);
    }
    if (json.max != null) {
      builder.max(json.max);
    }
    if (json.splitsRefined != null) {
      builder.splitsRefined(json.splitsRefined);
    }
    if (json.minInterval != null) {
      builder.minInterval(json.minInterval);
    }
    if (json.defaultValue != null) {
      builder.defaultValue(json.defaultValue);
    }
    if (json.isOptionalIsSet) {
      builder.isOptional(json.isOptional);
    }
    if (json.qualifier != null) {
      builder.qualifier(json.qualifier);
    }
    if (json.metaTags != null) {
      builder.addAllMetaTags(json.metaTags);
    }
    if (json.simpleName != null) {
      builder.simpleName(json.simpleName);
    }
    return builder.build();
  }

  @SuppressWarnings("Immutable")
  private transient volatile long lazyInitBitmap;

  private static final long PARAM_TYPE_LAZY_INIT_BIT = 0x1L;

  @SuppressWarnings("Immutable")
  private transient String paramType;

  /**
   * {@inheritDoc}
   * <p>
   * Returns a lazily initialized value of the {@link INumericParameter#getParamType() paramType} attribute.
   * Initialized once and only once and stored for subsequent access with proper synchronization.
   * In case of any exception or error thrown by the lazy value initializer,
   * the result will not be memoised (i.e. remembered) and on next call computation
   * will be attempted again.
   * @return A lazily initialized value of the {@code paramType} attribute
   */
  @Override
  public String getParamType() {
    if ((lazyInitBitmap & PARAM_TYPE_LAZY_INIT_BIT) == 0) {
      synchronized (this) {
        if ((lazyInitBitmap & PARAM_TYPE_LAZY_INIT_BIT) == 0) {
          this.paramType = Objects.requireNonNull(INumericParameter.super.getParamType(), "paramType");
          lazyInitBitmap |= PARAM_TYPE_LAZY_INIT_BIT;
        }
      }
    }
    return paramType;
  }

  /**
   * Creates an immutable copy of a {@link INumericParameter} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable NumericParameter instance
   */
  public static NumericParameter copyOf(INumericParameter instance) {
    if (instance instanceof NumericParameter) {
      return (NumericParameter) instance;
    }
    return NumericParameter.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link NumericParameter NumericParameter}.
   * <pre>
   * NumericParameter.builder()
   *    .isInteger(boolean) // optional {@link INumericParameter#isInteger() isInteger}
   *    .min(Double | null) // nullable {@link INumericParameter#getMin() min}
   *    .max(Double | null) // nullable {@link INumericParameter#getMax() max}
   *    .splitsRefined(Integer | null) // nullable {@link INumericParameter#getSplitsRefined() splitsRefined}
   *    .minInterval(Integer | null) // nullable {@link INumericParameter#getMinInterval() minInterval}
   *    .defaultValue(Double | null) // nullable {@link INumericParameter#getDefaultValue() defaultValue}
   *    .isOptional(boolean) // optional {@link INumericParameter#isOptional() isOptional}
   *    .qualifier(String) // required {@link INumericParameter#getQualifier() qualifier}
   *    .addMetaTags|addAllMetaTags(String) // {@link INumericParameter#getMetaTags() metaTags} elements
   *    .simpleName(String) // optional {@link INumericParameter#getSimpleName() simpleName}
   *    .build();
   * </pre>
   * @return A new NumericParameter builder
   */
  public static NumericParameter.Builder builder() {
    return new NumericParameter.Builder();
  }

  /**
   * Builds instances of type {@link NumericParameter NumericParameter}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "INumericParameter", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_QUALIFIER = 0x1L;
    private static final long OPT_BIT_IS_INTEGER = 0x1L;
    private static final long OPT_BIT_IS_OPTIONAL = 0x2L;
    private long initBits = 0x1L;
    private long optBits;

    private boolean isInteger;
    private @Nullable Double min;
    private @Nullable Double max;
    private @Nullable Integer splitsRefined;
    private @Nullable Integer minInterval;
    private @Nullable Double defaultValue;
    private boolean isOptional;
    private @Nullable String qualifier;
    private ImmutableList.Builder<String> metaTags = ImmutableList.builder();
    private @Nullable String simpleName;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableNumericParameter} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableNumericParameter instance) {
      Objects.requireNonNull(instance, "instance");
      isInteger(instance.isInteger());
      @Nullable Double minValue = instance.getMin();
      if (minValue != null) {
        min(minValue);
      }
      @Nullable Double maxValue = instance.getMax();
      if (maxValue != null) {
        max(maxValue);
      }
      @Nullable Integer splitsRefinedValue = instance.getSplitsRefined();
      if (splitsRefinedValue != null) {
        splitsRefined(splitsRefinedValue);
      }
      @Nullable Integer minIntervalValue = instance.getMinInterval();
      if (minIntervalValue != null) {
        minInterval(minIntervalValue);
      }
      @Nullable Double defaultValueValue = instance.getDefaultValue();
      if (defaultValueValue != null) {
        defaultValue(defaultValueValue);
      }
      isOptional(instance.isOptional());
      if (instance.qualifierIsSet()) {
        qualifier(instance.getQualifier());
      }
      addAllMetaTags(instance.getMetaTags());
      simpleName(instance.getSimpleName());
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.param.INumericParameter} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(INumericParameter instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.param.IParameter} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IParameter instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.IQualifiable} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IQualifiable instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof MutableNumericParameter) {
        from((MutableNumericParameter) object);
        return;
      }
      if (object instanceof INumericParameter) {
        INumericParameter instance = (INumericParameter) object;
        @Nullable Integer splitsRefinedValue = instance.getSplitsRefined();
        if (splitsRefinedValue != null) {
          splitsRefined(splitsRefinedValue);
        }
        isInteger(instance.isInteger());
        @Nullable Double minValue = instance.getMin();
        if (minValue != null) {
          min(minValue);
        }
        @Nullable Double maxValue = instance.getMax();
        if (maxValue != null) {
          max(maxValue);
        }
        @Nullable Integer minIntervalValue = instance.getMinInterval();
        if (minIntervalValue != null) {
          minInterval(minIntervalValue);
        }
        @Nullable Double defaultValueValue = instance.getDefaultValue();
        if (defaultValueValue != null) {
          defaultValue(defaultValueValue);
        }
      }
      if (object instanceof IParameter) {
        IParameter instance = (IParameter) object;
        isOptional(instance.isOptional());
      }
      if (object instanceof IQualifiable) {
        IQualifiable instance = (IQualifiable) object;
        addAllMetaTags(instance.getMetaTags());
        simpleName(instance.getSimpleName());
        qualifier(instance.getQualifier());
      }
    }

    /**
     * Initializes the value for the {@link INumericParameter#isInteger() isInteger} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link INumericParameter#isInteger() isInteger}.</em>
     * @param isInteger The value for isInteger 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("isInteger")
    public final Builder isInteger(boolean isInteger) {
      this.isInteger = isInteger;
      optBits |= OPT_BIT_IS_INTEGER;
      return this;
    }

    /**
     * Initializes the value for the {@link INumericParameter#getMin() min} attribute.
     * @param min The value for min (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("min")
    public final Builder min(@Nullable Double min) {
      this.min = min;
      return this;
    }

    /**
     * Initializes the value for the {@link INumericParameter#getMax() max} attribute.
     * @param max The value for max (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("max")
    public final Builder max(@Nullable Double max) {
      this.max = max;
      return this;
    }

    /**
     * Initializes the value for the {@link INumericParameter#getSplitsRefined() splitsRefined} attribute.
     * @param splitsRefined The value for splitsRefined (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("splitsRefined")
    public final Builder splitsRefined(@Nullable Integer splitsRefined) {
      this.splitsRefined = splitsRefined;
      return this;
    }

    /**
     * Initializes the value for the {@link INumericParameter#getMinInterval() minInterval} attribute.
     * @param minInterval The value for minInterval (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("minInterval")
    public final Builder minInterval(@Nullable Integer minInterval) {
      this.minInterval = minInterval;
      return this;
    }

    /**
     * Initializes the value for the {@link INumericParameter#getDefaultValue() defaultValue} attribute.
     * @param defaultValue The value for defaultValue (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("defaultValue")
    public final Builder defaultValue(@Nullable Double defaultValue) {
      this.defaultValue = defaultValue;
      return this;
    }

    /**
     * Initializes the value for the {@link INumericParameter#isOptional() isOptional} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link INumericParameter#isOptional() isOptional}.</em>
     * @param isOptional The value for isOptional 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("isOptional")
    public final Builder isOptional(boolean isOptional) {
      this.isOptional = isOptional;
      optBits |= OPT_BIT_IS_OPTIONAL;
      return this;
    }

    /**
     * Initializes the value for the {@link INumericParameter#getQualifier() qualifier} attribute.
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
     * Adds one element to {@link INumericParameter#getMetaTags() metaTags} list.
     * @param element A metaTags element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMetaTags(String element) {
      this.metaTags.add(element);
      return this;
    }

    /**
     * Adds elements to {@link INumericParameter#getMetaTags() metaTags} list.
     * @param elements An array of metaTags elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMetaTags(String... elements) {
      this.metaTags.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link INumericParameter#getMetaTags() metaTags} list.
     * @param elements An iterable of metaTags elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("metaTags")
    public final Builder metaTags(Iterable<String> elements) {
      this.metaTags = ImmutableList.builder();
      return addAllMetaTags(elements);
    }

    /**
     * Adds elements to {@link INumericParameter#getMetaTags() metaTags} list.
     * @param elements An iterable of metaTags elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllMetaTags(Iterable<String> elements) {
      this.metaTags.addAll(elements);
      return this;
    }

    /**
     * Initializes the value for the {@link INumericParameter#getSimpleName() simpleName} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link INumericParameter#getSimpleName() simpleName}.</em>
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
     * Builds a new {@link NumericParameter NumericParameter}.
     * @return An immutable instance of NumericParameter
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public NumericParameter build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new NumericParameter(this);
    }

    private boolean isIntegerIsSet() {
      return (optBits & OPT_BIT_IS_INTEGER) != 0;
    }

    private boolean isOptionalIsSet() {
      return (optBits & OPT_BIT_IS_OPTIONAL) != 0;
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_QUALIFIER) != 0) attributes.add("qualifier");
      return "Cannot build NumericParameter, some of required attributes are not set " + attributes;
    }
  }
}
