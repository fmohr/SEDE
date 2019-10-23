package de.upb.sede.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
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
 * {@code ImmutableIBooleanParameter.builder()}.
 */
@Generated(from = "IBooleanParameter", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ImmutableIBooleanParameter implements IBooleanParameter {
  private final @Nullable Boolean defaultValue;
  private final String paramType;
  private final String qualifier;
  private final ImmutableList<String> metaTags;
  private final String simpleName;

  private ImmutableIBooleanParameter(ImmutableIBooleanParameter.Builder builder) {
    this.defaultValue = builder.defaultValue;
    this.qualifier = builder.qualifier;
    this.metaTags = builder.metaTags.build();
    if (builder.paramType != null) {
      initShim.paramType(builder.paramType);
    }
    if (builder.simpleName != null) {
      initShim.simpleName(builder.simpleName);
    }
    this.paramType = initShim.getParamType();
    this.simpleName = initShim.getSimpleName();
    this.initShim = null;
  }

  private ImmutableIBooleanParameter(
      @Nullable Boolean defaultValue,
      String paramType,
      String qualifier,
      ImmutableList<String> metaTags,
      String simpleName) {
    this.defaultValue = defaultValue;
    this.paramType = paramType;
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
    private byte paramTypeBuildStage = STAGE_UNINITIALIZED;
    private String paramType;

    String getParamType() {
      if (paramTypeBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (paramTypeBuildStage == STAGE_UNINITIALIZED) {
        paramTypeBuildStage = STAGE_INITIALIZING;
        this.paramType = Objects.requireNonNull(getParamTypeInitialize(), "paramType");
        paramTypeBuildStage = STAGE_INITIALIZED;
      }
      return this.paramType;
    }

    void paramType(String paramType) {
      this.paramType = paramType;
      paramTypeBuildStage = STAGE_INITIALIZED;
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
      if (paramTypeBuildStage == STAGE_INITIALIZING) attributes.add("paramType");
      if (simpleNameBuildStage == STAGE_INITIALIZING) attributes.add("simpleName");
      return "Cannot build IBooleanParameter, attribute initializers form cycle " + attributes;
    }
  }

  private String getParamTypeInitialize() {
    return IBooleanParameter.super.getParamType();
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
   * @return The value of the {@code paramType} attribute
   */
  @JsonProperty("paramType")
  @Override
  public String getParamType() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.getParamType()
        : this.paramType;
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
  public final ImmutableIBooleanParameter withDefaultValue(@Nullable Boolean value) {
    if (Objects.equals(this.defaultValue, value)) return this;
    return new ImmutableIBooleanParameter(value, this.paramType, this.qualifier, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IBooleanParameter#getParamType() paramType} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for paramType
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableIBooleanParameter withParamType(String value) {
    String newValue = Objects.requireNonNull(value, "paramType");
    if (this.paramType.equals(newValue)) return this;
    return new ImmutableIBooleanParameter(this.defaultValue, newValue, this.qualifier, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IBooleanParameter#getQualifier() qualifier} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for qualifier
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableIBooleanParameter withQualifier(String value) {
    String newValue = Objects.requireNonNull(value, "qualifier");
    if (this.qualifier.equals(newValue)) return this;
    return new ImmutableIBooleanParameter(this.defaultValue, this.paramType, newValue, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IBooleanParameter#getMetaTags() metaTags}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableIBooleanParameter withMetaTags(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ImmutableIBooleanParameter(this.defaultValue, this.paramType, this.qualifier, newValue, this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link IBooleanParameter#getMetaTags() metaTags}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of metaTags elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableIBooleanParameter withMetaTags(Iterable<String> elements) {
    if (this.metaTags == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ImmutableIBooleanParameter(this.defaultValue, this.paramType, this.qualifier, newValue, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link IBooleanParameter#getSimpleName() simpleName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for simpleName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableIBooleanParameter withSimpleName(String value) {
    String newValue = Objects.requireNonNull(value, "simpleName");
    if (this.simpleName.equals(newValue)) return this;
    return new ImmutableIBooleanParameter(this.defaultValue, this.paramType, this.qualifier, this.metaTags, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableIBooleanParameter} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableIBooleanParameter
        && equalTo((ImmutableIBooleanParameter) another);
  }

  private boolean equalTo(ImmutableIBooleanParameter another) {
    return Objects.equals(defaultValue, another.defaultValue)
        && paramType.equals(another.paramType)
        && qualifier.equals(another.qualifier);
  }

  /**
   * Computes a hash code from attributes: {@code defaultValue}, {@code paramType}, {@code qualifier}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + Objects.hashCode(defaultValue);
    h += (h << 5) + paramType.hashCode();
    h += (h << 5) + qualifier.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code IBooleanParameter} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("IBooleanParameter")
        .omitNullValues()
        .add("defaultValue", defaultValue)
        .add("paramType", paramType)
        .add("qualifier", qualifier)
        .toString();
  }

  /**
   * Creates an immutable copy of a {@link IBooleanParameter} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable IBooleanParameter instance
   */
  public static ImmutableIBooleanParameter copyOf(IBooleanParameter instance) {
    if (instance instanceof ImmutableIBooleanParameter) {
      return (ImmutableIBooleanParameter) instance;
    }
    return ImmutableIBooleanParameter.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableIBooleanParameter ImmutableIBooleanParameter}.
   * <pre>
   * ImmutableIBooleanParameter.builder()
   *    .defaultValue(Boolean | null) // nullable {@link IBooleanParameter#getDefaultValue() defaultValue}
   *    .paramType(String) // optional {@link IBooleanParameter#getParamType() paramType}
   *    .qualifier(String) // required {@link IBooleanParameter#getQualifier() qualifier}
   *    .addMetaTags|addAllMetaTags(String) // {@link IBooleanParameter#getMetaTags() metaTags} elements
   *    .simpleName(String) // optional {@link IBooleanParameter#getSimpleName() simpleName}
   *    .build();
   * </pre>
   * @return A new ImmutableIBooleanParameter builder
   */
  public static ImmutableIBooleanParameter.Builder builder() {
    return new ImmutableIBooleanParameter.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableIBooleanParameter ImmutableIBooleanParameter}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "IBooleanParameter", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_QUALIFIER = 0x1L;
    private long initBits = 0x1L;

    private @Nullable Boolean defaultValue;
    private @Nullable String paramType;
    private @Nullable String qualifier;
    private ImmutableList.Builder<String> metaTags = ImmutableList.builder();
    private @Nullable String simpleName;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code ModifiableIBooleanParameter} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(ModifiableIBooleanParameter instance) {
      Objects.requireNonNull(instance, "instance");
      @Nullable Boolean defaultValueValue = instance.getDefaultValue();
      if (defaultValueValue != null) {
        defaultValue(defaultValueValue);
      }
      paramType(instance.getParamType());
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
      if (object instanceof ModifiableIBooleanParameter) {
        from((ModifiableIBooleanParameter) object);
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
        paramType(instance.getParamType());
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
     * Initializes the value for the {@link IBooleanParameter#getParamType() paramType} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link IBooleanParameter#getParamType() paramType}.</em>
     * @param paramType The value for paramType 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("paramType")
    public final Builder paramType(String paramType) {
      this.paramType = Objects.requireNonNull(paramType, "paramType");
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
     * Builds a new {@link ImmutableIBooleanParameter ImmutableIBooleanParameter}.
     * @return An immutable instance of IBooleanParameter
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableIBooleanParameter build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableIBooleanParameter(this);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_QUALIFIER) != 0) attributes.add("qualifier");
      return "Cannot build IBooleanParameter, some of required attributes are not set " + attributes;
    }
  }
}
