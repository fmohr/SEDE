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
 * Immutable implementation of {@link ICategoryParameter}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableICategoryParameter.builder()}.
 */
@Generated(from = "ICategoryParameter", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ImmutableICategoryParameter implements ICategoryParameter {
  private final @Nullable String defaultValue;
  private final ImmutableList<String> categories;
  private final String paramType;
  private final String qualifier;
  private final ImmutableList<String> metaTags;
  private final String simpleName;

  private ImmutableICategoryParameter(ImmutableICategoryParameter.Builder builder) {
    this.defaultValue = builder.defaultValue;
    this.categories = builder.categories.build();
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

  private ImmutableICategoryParameter(
      @Nullable String defaultValue,
      ImmutableList<String> categories,
      String paramType,
      String qualifier,
      ImmutableList<String> metaTags,
      String simpleName) {
    this.defaultValue = defaultValue;
    this.categories = categories;
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

  @Generated(from = "ICategoryParameter", generator = "Immutables")
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
      return "Cannot build ICategoryParameter, attribute initializers form cycle " + attributes;
    }
  }

  private String getParamTypeInitialize() {
    return ICategoryParameter.super.getParamType();
  }

  private String getSimpleNameInitialize() {
    return ICategoryParameter.super.getSimpleName();
  }

  /**
   * @return The value of the {@code defaultValue} attribute
   */
  @JsonProperty("defaultValue")
  @Override
  public @Nullable String getDefaultValue() {
    return defaultValue;
  }

  /**
   * @return The value of the {@code categories} attribute
   */
  @JsonProperty("categories")
  @Override
  public ImmutableList<String> getCategories() {
    return categories;
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
   * Copy the current immutable object by setting a value for the {@link ICategoryParameter#getDefaultValue() defaultValue} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for defaultValue (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableICategoryParameter withDefaultValue(@Nullable String value) {
    if (Objects.equals(this.defaultValue, value)) return this;
    return new ImmutableICategoryParameter(value, this.categories, this.paramType, this.qualifier, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ICategoryParameter#getCategories() categories}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableICategoryParameter withCategories(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ImmutableICategoryParameter(this.defaultValue, newValue, this.paramType, this.qualifier, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ICategoryParameter#getCategories() categories}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of categories elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableICategoryParameter withCategories(Iterable<String> elements) {
    if (this.categories == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ImmutableICategoryParameter(this.defaultValue, newValue, this.paramType, this.qualifier, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ICategoryParameter#getParamType() paramType} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for paramType
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableICategoryParameter withParamType(String value) {
    String newValue = Objects.requireNonNull(value, "paramType");
    if (this.paramType.equals(newValue)) return this;
    return new ImmutableICategoryParameter(this.defaultValue, this.categories, newValue, this.qualifier, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ICategoryParameter#getQualifier() qualifier} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for qualifier
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableICategoryParameter withQualifier(String value) {
    String newValue = Objects.requireNonNull(value, "qualifier");
    if (this.qualifier.equals(newValue)) return this;
    return new ImmutableICategoryParameter(this.defaultValue, this.categories, this.paramType, newValue, this.metaTags, this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ICategoryParameter#getMetaTags() metaTags}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableICategoryParameter withMetaTags(String... elements) {
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ImmutableICategoryParameter(this.defaultValue, this.categories, this.paramType, this.qualifier, newValue, this.simpleName);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link ICategoryParameter#getMetaTags() metaTags}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of metaTags elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableICategoryParameter withMetaTags(Iterable<String> elements) {
    if (this.metaTags == elements) return this;
    ImmutableList<String> newValue = ImmutableList.copyOf(elements);
    return new ImmutableICategoryParameter(this.defaultValue, this.categories, this.paramType, this.qualifier, newValue, this.simpleName);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ICategoryParameter#getSimpleName() simpleName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for simpleName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableICategoryParameter withSimpleName(String value) {
    String newValue = Objects.requireNonNull(value, "simpleName");
    if (this.simpleName.equals(newValue)) return this;
    return new ImmutableICategoryParameter(this.defaultValue, this.categories, this.paramType, this.qualifier, this.metaTags, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableICategoryParameter} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableICategoryParameter
        && equalTo((ImmutableICategoryParameter) another);
  }

  private boolean equalTo(ImmutableICategoryParameter another) {
    return Objects.equals(defaultValue, another.defaultValue)
        && categories.equals(another.categories)
        && paramType.equals(another.paramType)
        && qualifier.equals(another.qualifier);
  }

  /**
   * Computes a hash code from attributes: {@code defaultValue}, {@code categories}, {@code paramType}, {@code qualifier}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + Objects.hashCode(defaultValue);
    h += (h << 5) + categories.hashCode();
    h += (h << 5) + paramType.hashCode();
    h += (h << 5) + qualifier.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code ICategoryParameter} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ICategoryParameter")
        .omitNullValues()
        .add("defaultValue", defaultValue)
        .add("categories", categories)
        .add("paramType", paramType)
        .add("qualifier", qualifier)
        .toString();
  }

  /**
   * Creates an immutable copy of a {@link ICategoryParameter} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ICategoryParameter instance
   */
  public static ImmutableICategoryParameter copyOf(ICategoryParameter instance) {
    if (instance instanceof ImmutableICategoryParameter) {
      return (ImmutableICategoryParameter) instance;
    }
    return ImmutableICategoryParameter.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableICategoryParameter ImmutableICategoryParameter}.
   * <pre>
   * ImmutableICategoryParameter.builder()
   *    .defaultValue(String | null) // nullable {@link ICategoryParameter#getDefaultValue() defaultValue}
   *    .addCategories|addAllCategories(String) // {@link ICategoryParameter#getCategories() categories} elements
   *    .paramType(String) // optional {@link ICategoryParameter#getParamType() paramType}
   *    .qualifier(String) // required {@link ICategoryParameter#getQualifier() qualifier}
   *    .addMetaTags|addAllMetaTags(String) // {@link ICategoryParameter#getMetaTags() metaTags} elements
   *    .simpleName(String) // optional {@link ICategoryParameter#getSimpleName() simpleName}
   *    .build();
   * </pre>
   * @return A new ImmutableICategoryParameter builder
   */
  public static ImmutableICategoryParameter.Builder builder() {
    return new ImmutableICategoryParameter.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableICategoryParameter ImmutableICategoryParameter}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "ICategoryParameter", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_QUALIFIER = 0x1L;
    private long initBits = 0x1L;

    private @Nullable String defaultValue;
    private ImmutableList.Builder<String> categories = ImmutableList.builder();
    private @Nullable String paramType;
    private @Nullable String qualifier;
    private ImmutableList.Builder<String> metaTags = ImmutableList.builder();
    private @Nullable String simpleName;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code ModifiableICategoryParameter} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(ModifiableICategoryParameter instance) {
      Objects.requireNonNull(instance, "instance");
      @Nullable String defaultValueValue = instance.getDefaultValue();
      if (defaultValueValue != null) {
        defaultValue(defaultValueValue);
      }
      addAllCategories(instance.getCategories());
      paramType(instance.getParamType());
      if (instance.qualifierIsSet()) {
        qualifier(instance.getQualifier());
      }
      addAllMetaTags(instance.getMetaTags());
      simpleName(instance.getSimpleName());
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

    /**
     * Fill a builder with attribute values from the provided {@code de.upb.sede.param.ICategoryParameter} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(ICategoryParameter instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof ModifiableICategoryParameter) {
        from((ModifiableICategoryParameter) object);
        return;
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
      if (object instanceof ICategoryParameter) {
        ICategoryParameter instance = (ICategoryParameter) object;
        @Nullable String defaultValueValue = instance.getDefaultValue();
        if (defaultValueValue != null) {
          defaultValue(defaultValueValue);
        }
        addAllCategories(instance.getCategories());
      }
    }

    /**
     * Initializes the value for the {@link ICategoryParameter#getDefaultValue() defaultValue} attribute.
     * @param defaultValue The value for defaultValue (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("defaultValue")
    public final Builder defaultValue(@Nullable String defaultValue) {
      this.defaultValue = defaultValue;
      return this;
    }

    /**
     * Adds one element to {@link ICategoryParameter#getCategories() categories} list.
     * @param element A categories element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addCategories(String element) {
      this.categories.add(element);
      return this;
    }

    /**
     * Adds elements to {@link ICategoryParameter#getCategories() categories} list.
     * @param elements An array of categories elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addCategories(String... elements) {
      this.categories.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link ICategoryParameter#getCategories() categories} list.
     * @param elements An iterable of categories elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("categories")
    public final Builder categories(Iterable<String> elements) {
      this.categories = ImmutableList.builder();
      return addAllCategories(elements);
    }

    /**
     * Adds elements to {@link ICategoryParameter#getCategories() categories} list.
     * @param elements An iterable of categories elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllCategories(Iterable<String> elements) {
      this.categories.addAll(elements);
      return this;
    }

    /**
     * Initializes the value for the {@link ICategoryParameter#getParamType() paramType} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link ICategoryParameter#getParamType() paramType}.</em>
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
     * Initializes the value for the {@link ICategoryParameter#getQualifier() qualifier} attribute.
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
     * Adds one element to {@link ICategoryParameter#getMetaTags() metaTags} list.
     * @param element A metaTags element
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMetaTags(String element) {
      this.metaTags.add(element);
      return this;
    }

    /**
     * Adds elements to {@link ICategoryParameter#getMetaTags() metaTags} list.
     * @param elements An array of metaTags elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addMetaTags(String... elements) {
      this.metaTags.add(elements);
      return this;
    }


    /**
     * Sets or replaces all elements for {@link ICategoryParameter#getMetaTags() metaTags} list.
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
     * Adds elements to {@link ICategoryParameter#getMetaTags() metaTags} list.
     * @param elements An iterable of metaTags elements
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder addAllMetaTags(Iterable<String> elements) {
      this.metaTags.addAll(elements);
      return this;
    }

    /**
     * Initializes the value for the {@link ICategoryParameter#getSimpleName() simpleName} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link ICategoryParameter#getSimpleName() simpleName}.</em>
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
     * Builds a new {@link ImmutableICategoryParameter ImmutableICategoryParameter}.
     * @return An immutable instance of ICategoryParameter
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableICategoryParameter build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableICategoryParameter(this);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_QUALIFIER) != 0) attributes.add("qualifier");
      return "Cannot build ICategoryParameter, some of required attributes are not set " + attributes;
    }
  }
}
