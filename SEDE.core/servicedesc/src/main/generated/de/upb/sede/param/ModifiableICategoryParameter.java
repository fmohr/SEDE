package de.upb.sede.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
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
 * <p><em>ModifiableICategoryParameter is not thread-safe</em>
 * @see ImmutableICategoryParameter
 */
@Generated(from = "ICategoryParameter", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "ICategoryParameter"})
@NotThreadSafe
public final class ModifiableICategoryParameter implements ICategoryParameter {
  private static final long INIT_BIT_QUALIFIER = 0x1L;
  private long initBits = 0x1L;

  private @Nullable String defaultValue;
  private final ArrayList<String> categories = new ArrayList<String>();
  private String paramType;
  private String qualifier;
  private final ArrayList<String> metaTags = new ArrayList<String>();
  private String simpleName;

  private ModifiableICategoryParameter() {}

  /**
   * Construct a modifiable instance of {@code ICategoryParameter}.
   * @return A new modifiable instance
   */
  public static ModifiableICategoryParameter create() {
    return new ModifiableICategoryParameter();
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
  public ModifiableICategoryParameter clear() {
    initBits = 0x1L;
    defaultValue = null;
    categories.clear();
    paramType = null;
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
  public ModifiableICategoryParameter from(IParameter instance) {
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
  public ModifiableICategoryParameter from(IQualifiable instance) {
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
  public ModifiableICategoryParameter from(ICategoryParameter instance) {
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
  public ModifiableICategoryParameter from(ModifiableICategoryParameter instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof ModifiableICategoryParameter) {
      ModifiableICategoryParameter instance = (ModifiableICategoryParameter) object;
      @Nullable String defaultValueValue = instance.getDefaultValue();
      if (defaultValueValue != null) {
        setDefaultValue(defaultValueValue);
      }
      addAllCategories(instance.getCategories());
      setParamType(instance.getParamType());
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
  public ModifiableICategoryParameter setDefaultValue(@Nullable String defaultValue) {
    this.defaultValue = defaultValue;
    return this;
  }

  /**
   * Adds one element to {@link ICategoryParameter#getCategories() categories} list.
   * @param element The categories element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableICategoryParameter addCategories(String element) {
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
  public final ModifiableICategoryParameter addCategories(String... elements) {
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
  public ModifiableICategoryParameter setCategories(Iterable<String> elements) {
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
  public ModifiableICategoryParameter addAllCategories(Iterable<String> elements) {
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
  public ModifiableICategoryParameter setParamType(String paramType) {
    this.paramType = Objects.requireNonNull(paramType, "paramType");
    return this;
  }

  /**
   * Assigns a value to the {@link ICategoryParameter#getQualifier() qualifier} attribute.
   * @param qualifier The value for qualifier
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public ModifiableICategoryParameter setQualifier(String qualifier) {
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
  public ModifiableICategoryParameter addMetaTags(String element) {
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
  public final ModifiableICategoryParameter addMetaTags(String... elements) {
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
  public ModifiableICategoryParameter setMetaTags(Iterable<String> elements) {
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
  public ModifiableICategoryParameter addAllMetaTags(Iterable<String> elements) {
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
  public ModifiableICategoryParameter setSimpleName(String simpleName) {
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
  public final ModifiableICategoryParameter unsetQualifier() {
    initBits |= INIT_BIT_QUALIFIER;
    qualifier = null;
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
    return "ICategoryParameter is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link ImmutableICategoryParameter ImmutableICategoryParameter}.
   * @return An immutable instance of ICategoryParameter
   */
  public final ImmutableICategoryParameter toImmutable() {
    checkRequiredAttributes();
    return ImmutableICategoryParameter.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code ModifiableICategoryParameter} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof ModifiableICategoryParameter)) return false;
    ModifiableICategoryParameter other = (ModifiableICategoryParameter) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(ModifiableICategoryParameter another) {
    String paramType = getParamType();
    return Objects.equals(defaultValue, another.defaultValue)
        && categories.equals(another.categories)
        && paramType.equals(another.getParamType())
        && qualifier.equals(another.qualifier);
  }

  /**
   * Computes a hash code from attributes: {@code defaultValue}, {@code categories}, {@code paramType}, {@code qualifier}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + Objects.hashCode(defaultValue);
    h += (h << 5) + categories.hashCode();
    String paramType = getParamType();
    h += (h << 5) + paramType.hashCode();
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
    return MoreObjects.toStringHelper("ModifiableICategoryParameter")
        .add("defaultValue", getDefaultValue())
        .add("categories", getCategories())
        .add("paramType", getParamType())
        .add("qualifier", qualifierIsSet() ? getQualifier() : "?")
        .toString();
  }
}
