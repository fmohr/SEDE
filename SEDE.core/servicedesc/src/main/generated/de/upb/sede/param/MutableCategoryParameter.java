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
 * A modifiable implementation of the {@link ICategoryParameter ICategoryParameter} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableCategoryParameter is not thread-safe</em>
 * @see CategoryParameter
 */
@Generated(from = "ICategoryParameter", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "ICategoryParameter"})
@NotThreadSafe
public final class MutableCategoryParameter implements ICategoryParameter {
  private static final long INIT_BIT_QUALIFIER = 0x1L;
  private static final long OPT_BIT_IS_OPTIONAL = 0x1L;
  private long initBits = 0x1L;
  private long optBits;

  private @Nullable String defaultValue;
  private final ArrayList<String> categories = new ArrayList<String>();
  private String paramType;
  private boolean isOptional;
  private String qualifier;
  private final ArrayList<String> metaTags = new ArrayList<String>();
  private String simpleName;

  private MutableCategoryParameter() {}

  /**
   * Construct a modifiable instance of {@code ICategoryParameter}.
   * @return A new modifiable instance
   */
  public static MutableCategoryParameter create() {
    return new MutableCategoryParameter();
  }

  /**
   * @return value of {@code defaultValue} attribute, may be {@code null}
   */
  @JsonProperty("defaultValue")
  @Override
  public final @Nullable String getDefaultValue() {
    return defaultValue;
  }

  /**
   * @return modifiable list {@code categories}
   */
  @JsonProperty("categories")
  @Override
  public final List<String> getCategories() {
    return categories;
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code paramType} attribute
   */
  @JsonProperty("paramType")
  @Override
  public final String getParamType() {
    return paramTypeIsSet()
        ? paramType
        : ICategoryParameter.super.getParamType();
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code isOptional} attribute
   */
  @JsonProperty("isOptional")
  @Override
  public final boolean isOptional() {
    return isOptionalIsSet()
        ? isOptional
        : ICategoryParameter.super.isOptional();
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
        : ICategoryParameter.super.getSimpleName();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCategoryParameter clear() {
    initBits = 0x1L;
    optBits = 0;
    defaultValue = null;
    categories.clear();
    paramType = null;
    isOptional = false;
    qualifier = null;
    metaTags.clear();
    simpleName = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.param.IParameter} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCategoryParameter from(IParameter instance) {
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
  public MutableCategoryParameter from(IQualifiable instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.param.ICategoryParameter} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCategoryParameter from(ICategoryParameter instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link ICategoryParameter} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableCategoryParameter from(MutableCategoryParameter instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableCategoryParameter) {
      MutableCategoryParameter instance = (MutableCategoryParameter) object;
      @Nullable String defaultValueValue = instance.getDefaultValue();
      if (defaultValueValue != null) {
        setDefaultValue(defaultValueValue);
      }
      addAllCategories(instance.getCategories());
      setParamType(instance.getParamType());
      setIsOptional(instance.isOptional());
      if (instance.qualifierIsSet()) {
        setQualifier(instance.getQualifier());
      }
      addAllMetaTags(instance.getMetaTags());
      setSimpleName(instance.getSimpleName());
      return;
    }
    if (object instanceof IParameter) {
      IParameter instance = (IParameter) object;
      setParamType(instance.getParamType());
      setIsOptional(instance.isOptional());
    }
    if (object instanceof IQualifiable) {
      IQualifiable instance = (IQualifiable) object;
      addAllMetaTags(instance.getMetaTags());
      setSimpleName(instance.getSimpleName());
      setQualifier(instance.getQualifier());
    }
    if (object instanceof ICategoryParameter) {
      ICategoryParameter instance = (ICategoryParameter) object;
      @Nullable String defaultValueValue = instance.getDefaultValue();
      if (defaultValueValue != null) {
        setDefaultValue(defaultValueValue);
      }
      addAllCategories(instance.getCategories());
    }
  }

  /**
   * Assigns a value to the {@link ICategoryParameter#getDefaultValue() defaultValue} attribute.
   * @param defaultValue The value for defaultValue, can be {@code null}
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCategoryParameter setDefaultValue(@Nullable String defaultValue) {
    this.defaultValue = defaultValue;
    return this;
  }

  /**
   * Adds one element to {@link ICategoryParameter#getCategories() categories} list.
   * @param element The categories element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCategoryParameter addCategories(String element) {
    Objects.requireNonNull(element, "categories element");
    this.categories.add(element);
    return this;
  }

  /**
   * Adds elements to {@link ICategoryParameter#getCategories() categories} list.
   * @param elements An array of categories elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableCategoryParameter addCategories(String... elements) {
    for (String e : elements) {
      addCategories(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link ICategoryParameter#getCategories() categories} list.
   * @param elements An iterable of categories elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCategoryParameter setCategories(Iterable<String> elements) {
    this.categories.clear();
    addAllCategories(elements);
    return this;
  }

  /**
   * Adds elements to {@link ICategoryParameter#getCategories() categories} list.
   * @param elements An iterable of categories elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCategoryParameter addAllCategories(Iterable<String> elements) {
    for (String e : elements) {
      addCategories(e);
    }
    return this;
  }

  /**
   * Assigns a value to the {@link ICategoryParameter#getParamType() paramType} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link ICategoryParameter#getParamType() paramType}.</em>
   * @param paramType The value for paramType
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCategoryParameter setParamType(String paramType) {
    this.paramType = Objects.requireNonNull(paramType, "paramType");
    return this;
  }

  /**
   * Assigns a value to the {@link ICategoryParameter#isOptional() isOptional} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link ICategoryParameter#isOptional() isOptional}.</em>
   * @param isOptional The value for isOptional
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCategoryParameter setIsOptional(boolean isOptional) {
    this.isOptional = isOptional;
    optBits |= OPT_BIT_IS_OPTIONAL;
    return this;
  }

  /**
   * Assigns a value to the {@link ICategoryParameter#getQualifier() qualifier} attribute.
   * @param qualifier The value for qualifier
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCategoryParameter setQualifier(String qualifier) {
    this.qualifier = Objects.requireNonNull(qualifier, "qualifier");
    initBits &= ~INIT_BIT_QUALIFIER;
    return this;
  }

  /**
   * Adds one element to {@link ICategoryParameter#getMetaTags() metaTags} list.
   * @param element The metaTags element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCategoryParameter addMetaTags(String element) {
    Objects.requireNonNull(element, "metaTags element");
    this.metaTags.add(element);
    return this;
  }

  /**
   * Adds elements to {@link ICategoryParameter#getMetaTags() metaTags} list.
   * @param elements An array of metaTags elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableCategoryParameter addMetaTags(String... elements) {
    for (String e : elements) {
      addMetaTags(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link ICategoryParameter#getMetaTags() metaTags} list.
   * @param elements An iterable of metaTags elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCategoryParameter setMetaTags(Iterable<String> elements) {
    this.metaTags.clear();
    addAllMetaTags(elements);
    return this;
  }

  /**
   * Adds elements to {@link ICategoryParameter#getMetaTags() metaTags} list.
   * @param elements An iterable of metaTags elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCategoryParameter addAllMetaTags(Iterable<String> elements) {
    for (String e : elements) {
      addMetaTags(e);
    }
    return this;
  }

  /**
   * Assigns a value to the {@link ICategoryParameter#getSimpleName() simpleName} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link ICategoryParameter#getSimpleName() simpleName}.</em>
   * @param simpleName The value for simpleName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableCategoryParameter setSimpleName(String simpleName) {
    this.simpleName = Objects.requireNonNull(simpleName, "simpleName");
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link ICategoryParameter#getQualifier() qualifier} is set.
   * @return {@code true} if set
   */
  public final boolean qualifierIsSet() {
    return (initBits & INIT_BIT_QUALIFIER) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link ICategoryParameter#isOptional() isOptional} is set.
   * @return {@code true} if set
   */
  public final boolean isOptionalIsSet() {
    return (optBits & OPT_BIT_IS_OPTIONAL) != 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link ICategoryParameter#getParamType() paramType} is set.
   * @return {@code true} if set
   */
  public final boolean paramTypeIsSet() {
    return paramType != null;
  }

  /**
   * Returns {@code true} if the default attribute {@link ICategoryParameter#getSimpleName() simpleName} is set.
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
  public final MutableCategoryParameter unsetQualifier() {
    initBits |= INIT_BIT_QUALIFIER;
    qualifier = null;
    return this;
  }
  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableCategoryParameter unsetIsOptional() {
    optBits |= 0;
    isOptional = false;
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
    return "CategoryParameter is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link CategoryParameter CategoryParameter}.
   * @return An immutable instance of CategoryParameter
   */
  public final CategoryParameter toImmutable() {
    checkRequiredAttributes();
    return CategoryParameter.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableCategoryParameter} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableCategoryParameter)) return false;
    MutableCategoryParameter other = (MutableCategoryParameter) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableCategoryParameter another) {
    String paramType = getParamType();
    boolean isOptional = isOptional();
    return Objects.equals(defaultValue, another.defaultValue)
        && categories.equals(another.categories)
        && paramType.equals(another.getParamType())
        && isOptional == another.isOptional()
        && qualifier.equals(another.qualifier);
  }

  /**
   * Computes a hash code from attributes: {@code defaultValue}, {@code categories}, {@code paramType}, {@code isOptional}, {@code qualifier}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + Objects.hashCode(defaultValue);
    h += (h << 5) + categories.hashCode();
    String paramType = getParamType();
    h += (h << 5) + paramType.hashCode();
    boolean isOptional = isOptional();
    h += (h << 5) + Booleans.hashCode(isOptional);
    h += (h << 5) + qualifier.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code ICategoryParameter}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableCategoryParameter")
        .add("defaultValue", getDefaultValue())
        .add("categories", getCategories())
        .add("paramType", getParamType())
        .add("isOptional", isOptional())
        .add("qualifier", qualifierIsSet() ? getQualifier() : "?")
        .toString();
  }
}
