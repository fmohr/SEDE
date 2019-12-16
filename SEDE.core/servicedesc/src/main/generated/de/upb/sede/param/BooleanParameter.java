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
 * Immutable implementation of {@link IBooleanParameter}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code BooleanParameter.builder()}.
 */
@Generated(from = "IBooleanParameter", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class BooleanParameter implements IBooleanParameter {
  private final @Nullable Boolean defaultValue;
  private final boolean isOptional;
  private final String qualifier;
  private final ImmutableList<String> metaTags;
  private final String simpleName;

  private BooleanParameter(BooleanParameter.Builder builder) {
    this.defaultValue = builder.defaultValue;
    this.qualifier = builder.qualifier;
    this.metaTags = builder.metaTags.build();
    if (builder.isOptionalIsSet()) {
      initShim.isOptional(builder.isOptional);
    }
    if (builder.simpleName != null) {
      initShim.simpleName(builder.simpleName);
    }
    this.isOptional = initShim.isOptional();
    this.simpleName = initShim.getSimpleName();
    this.initShim = null;
  }

  private BooleanParameter(
      @Nullable Boolean defaultValue,
      boolean isOptional,
      String qualifier,
      ImmutableList<String> metaTags,
      String simpleName) {
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

  @Generated(from = "IBooleanParameter", generator = "Immutables")
  private final class InitShim {
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
      if (isOptionalBuildStage == STAGE_INITIALIZING) attributes.add("isOptional");
      if (simpleNameBuildStage == STAGE_INITIALIZING) attributes.add("simpleName");
      return "Cannot build BooleanParameter, attribute initializers form cycle " + attributes;
    }
  }

  private boolean isOptionalInitialize() {
    return IBooleanParameter.super.isOptional();
  }

  private String getSimpleNameInitialize() {
    return IBooleanParameter.super.getSimpleName();
  }

  /**
   * @return The value of the {@code defaultValue} attribute
   */
  @JsonProperty("defaultValue")
  @Override
  public @Nullable Boolean getDefaultValue() {
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
   * Copy the current immutable object by setting a value for the {@link IBooleanParameter#getDefaultValue() defaultValue} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for defaultValue (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final BooleanParameter withDefaultValue(@Nullable Boolean value) {
    if (Objects.equals(this.defaultValue, value)) return this;
    return new BooleanParameter(value, this.isOptional, this.qualifier, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IBooleanParameter#isOptional() isOptional} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for isOptional
   * @return A modified copy of the {@code this} object
   */
  public final BooleanParameter withIsOptional(boolean value) {
    if (this.isOptional == value) return this;
    return new BooleanParameter(this.defaultValue, value, this.qualifier, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IBooleanParameter#getQualifier() qualifier} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for qualifier
   * @return A modified copy of the {@code this} object
   */
  public final BooleanParameter withQualifier(String value) {
    String newValue = Objects.requireNonNull(value, "qualifier");
    if (this.qualifier.equals(newValue)) return this;
    return new BooleanParameter(this.defaultValue, this.isOptional, newValue, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IBooleanParameter#getMetaTags() metaTags}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final BooleanParameter withMetaTags(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new BooleanParameter(this.defaultValue, this.isOptional, this.qualifier, newValue, this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IBooleanParameter#getMetaTags() metaTags}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of metaTags elements to set
   * @return A modified copy of {@code this} object
   */
  public final BooleanParameter withMetaTags(Iterable<String> elements) {
    if (this.metaTags == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new BooleanParameter(this.defaultValue, this.isOptional, this.qualifier, newValue, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IBooleanParameter#getSimpleName() simpleName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for simpleName
   * @return A modified copy of the {@code this} object
   */
  public final BooleanParameter withSimpleName(String value) {
    String newValue = Objects.requireNonNull(value, "simpleName");
    if (this.simpleName.equals(newValue)) return this;
    return new BooleanParameter(this.defaultValue, this.isOptional, this.qualifier, this.metaTags, newValue);
  }

  /**
   * This instance is equal to all instances of {@code BooleanParameter} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof BooleanParameter
        && equalTo((BooleanParameter) another);
  }

  private boolean equalTo(BooleanParameter another) {
    return Objects.equals(defaultValue, another.defaultValue)
        && isOptional == another.isOptional
        && qualifier.equals(another.qualifier);
  }

  /**
   * Computes a hash code from attributes: {@code defaultValue}, {@code isOptional}, {@code qualifier}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + Objects.hashCode(defaultValue);
    h += (h << 5) + Booleans.hashCode(isOptional);
    h += (h << 5) + qualifier.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code BooleanParameter} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("BooleanParameter")
        .omitNullValues()
        .add("defaultValue", defaultValue)
        .add("isOptional", isOptional)
        .add("qualifier", qualifier)
        .toString();
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "IBooleanParameter", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements IBooleanParameter {
    @Nullable Boolean defaultValue;
    boolean isOptional;
    boolean isOptionalIsSet;
    @Nullable String qualifier;
    @Nullable List<String> metaTags = ImmutableList.of();
    @Nullable String simpleName;
    @JsonProperty("defaultValue")
    public void setDefaultValue(@Nullable Boolean defaultValue) {
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
    public Boolean getDefaultValue() { throw new UnsupportedOperationException(); }
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
  static BooleanParameter fromJson(Json json) {
    BooleanParameter.Builder builder = BooleanParameter.builder();
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
   * Returns a lazily initialized value of the {@link IBooleanParameter#getParamType() paramType} attribute.
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
          this.paramType = Objects.requireNonNull(IBooleanParameter.super.getParamType(), "paramType");
          lazyInitBitmap |= PARAM_TYPE_LAZY_INIT_BIT;
        }
      }
    }
    return paramType;
  }

  /**
   * Creates an immutable copy of a {@link IBooleanParameter} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable BooleanParameter instance
   */
  public static BooleanParameter copyOf(IBooleanParameter instance) {
    if (instance instanceof BooleanParameter) {
      return (BooleanParameter) instance;
    }
    return BooleanParameter.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link BooleanParameter BooleanParameter}.
   * <pre>
   * BooleanParameter.builder()
   *    .defaultValue(Boolean | null) // nullable {@link IBooleanParameter#getDefaultValue() defaultValue}
   *    .isOptional(boolean) // optional {@link IBooleanParameter#isOptional() isOptional}
   *    .qualifier(String) // required {@link IBooleanParameter#getQualifier() qualifier}
   *    .addMetaTags|addAllMetaTags(String) // {@link IBooleanParameter#getMetaTags() metaTags} elements
   *    .simpleName(String) // optional {@link IBooleanParameter#getSimpleName() simpleName}
   *    .build();
   * </pre>
   * @return A new BooleanParameter builder
   */
  public static BooleanParameter.Builder builder() {
    return new BooleanParameter.Builder();
  }

  /**
   * Builds instances of type {@link BooleanParameter BooleanParameter}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IBooleanParameter", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_QUALIFIER = 0x1L;
    private static final long OPT_BIT_IS_OPTIONAL = 0x1L;
    private long initBits = 0x1L;
    private long optBits;

    private @Nullable Boolean defaultValue;
    private boolean isOptional;
    private @Nullable String qualifier;
    private ImmutableList.Builder<String> metaTags = ImmutableList.builder();
    private @Nullable String simpleName;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code MutableBooleanParameter} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(MutableBooleanParameter instance) {
      Objects.requireNonNull(instance, "instance");
      @Nullable Boolean defaultValueValue = instance.getDefaultValue();
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
     * Fill a builder with attribute values from the provided {@code de.upb.sede.param.IBooleanParameter} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(IBooleanParameter instance) {
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
      if (object instanceof MutableBooleanParameter) {
        from((MutableBooleanParameter) object);
        return;
      }
      if (object instanceof IBooleanParameter) {
        IBooleanParameter instance = (IBooleanParameter) object;
        @Nullable Boolean defaultValueValue = instance.getDefaultValue();
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
     * Initializes the value for the {@link IBooleanParameter#getDefaultValue() defaultValue} attribute.
     * @param defaultValue The value for defaultValue (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("defaultValue")
    public final Builder defaultValue(@Nullable Boolean defaultValue) {
      this.defaultValue = defaultValue;
      return this;
    }

    /**
     * Initializes the value for the {@link IBooleanParameter#isOptional() isOptional} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IBooleanParameter#isOptional() isOptional}.</em>
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
     * Initializes the value for the {@link IBooleanParameter#getQualifier() qualifier} attribute.
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
     * Adds one element to {@link IBooleanParameter#getMetaTags() metaTags} list.
     * @param element A metaTags element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMetaTags(String element) {
      this.metaTags.add(element);
      return this;
    }

    /**
     * Adds elements to {@link IBooleanParameter#getMetaTags() metaTags} list.
     * @param elements An array of metaTags elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMetaTags(String... elements) {
      this.metaTags.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link IBooleanParameter#getMetaTags() metaTags} list.
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
     * Adds elements to {@link IBooleanParameter#getMetaTags() metaTags} list.
     * @param elements An iterable of metaTags elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllMetaTags(Iterable<String> elements) {
      this.metaTags.addAll(elements);
      return this;
    }

    /**
     * Initializes the value for the {@link IBooleanParameter#getSimpleName() simpleName} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IBooleanParameter#getSimpleName() simpleName}.</em>
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
     * Builds a new {@link BooleanParameter BooleanParameter}.
     * @return An immutable instance of BooleanParameter
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public BooleanParameter build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new BooleanParameter(this);
    }

    private boolean isOptionalIsSet() {
      return (optBits & OPT_BIT_IS_OPTIONAL) != 0;
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_QUALIFIER) != 0) attributes.add("qualifier");
      return "Cannot build BooleanParameter, some of required attributes are not set " + attributes;
    }
  }
}
