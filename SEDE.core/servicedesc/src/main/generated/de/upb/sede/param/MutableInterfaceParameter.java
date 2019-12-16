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
 * A modifiable implementation of the {@link IInterfaceParameter IInterfaceParameter} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>MutableInterfaceParameter is not thread-safe</em>
 * @see InterfaceParameter
 */
@Generated(from = "IInterfaceParameter", generator = "Modifiables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.Generated({"Modifiables.generator", "IInterfaceParameter"})
@NotThreadSafe
public final class MutableInterfaceParameter implements IInterfaceParameter {
  private static final long INIT_BIT_INTERFACE_QUALIFIER = 0x1L;
  private static final long INIT_BIT_QUALIFIER = 0x2L;
  private static final long OPT_BIT_IS_OPTIONAL = 0x1L;
  private long initBits = 0x3L;
  private long optBits;

  private String interfaceQualifier;
  private boolean isOptional;
  private String qualifier;
  private final ArrayList<String> metaTags = new ArrayList<String>();
  private String simpleName;

  private MutableInterfaceParameter() {}

  /**
   * Construct a modifiable instance of {@code IInterfaceParameter}.
   * @return A new modifiable instance
   */
  public static MutableInterfaceParameter create() {
    return new MutableInterfaceParameter();
  }

  /**
   * @return value of {@code interfaceQualifier} attribute
   */
  @JsonProperty("interfaceQualifier")
  @Override
  public final String getInterfaceQualifier() {
    if (!interfaceQualifierIsSet()) {
      checkRequiredAttributes();
    }
    return interfaceQualifier;
  }

  /**
   * @return assigned or, otherwise, newly computed, not cached value of {@code isOptional} attribute
   */
  @JsonProperty("isOptional")
  @Override
  public final boolean isOptional() {
    return isOptionalIsSet()
        ? isOptional
        : IInterfaceParameter.super.isOptional();
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
        : IInterfaceParameter.super.getSimpleName();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInterfaceParameter clear() {
    initBits = 0x3L;
    optBits = 0;
    interfaceQualifier = null;
    isOptional = false;
    qualifier = null;
    metaTags.clear();
    simpleName = null;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link de.upb.sede.param.IInterfaceParameter} instance.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInterfaceParameter from(IInterfaceParameter instance) {
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
  public MutableInterfaceParameter from(IParameter instance) {
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
  public MutableInterfaceParameter from(IQualifiable instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link IInterfaceParameter} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * Collection elements and entries will be added, not replaced.
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public MutableInterfaceParameter from(MutableInterfaceParameter instance) {
    Objects.requireNonNull(instance, "instance");
    from((Object) instance);
    return this;
  }

  private void from(Object object) {
    if (object instanceof MutableInterfaceParameter) {
      MutableInterfaceParameter instance = (MutableInterfaceParameter) object;
      if (instance.interfaceQualifierIsSet()) {
        setInterfaceQualifier(instance.getInterfaceQualifier());
      }
      setIsOptional(instance.isOptional());
      if (instance.qualifierIsSet()) {
        setQualifier(instance.getQualifier());
      }
      addAllMetaTags(instance.getMetaTags());
      setSimpleName(instance.getSimpleName());
      return;
    }
    if (object instanceof IInterfaceParameter) {
      IInterfaceParameter instance = (IInterfaceParameter) object;
      setInterfaceQualifier(instance.getInterfaceQualifier());
    }
    if (object instanceof IParameter) {
      IParameter instance = (IParameter) object;
      setIsOptional(instance.isOptional());
    }
    if (object instanceof IQualifiable) {
      IQualifiable instance = (IQualifiable) object;
      addAllMetaTags(instance.getMetaTags());
      setSimpleName(instance.getSimpleName());
      setQualifier(instance.getQualifier());
    }
  }

  /**
   * Assigns a value to the {@link IInterfaceParameter#getInterfaceQualifier() interfaceQualifier} attribute.
   * @param interfaceQualifier The value for interfaceQualifier
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInterfaceParameter setInterfaceQualifier(String interfaceQualifier) {
    this.interfaceQualifier = Objects.requireNonNull(interfaceQualifier, "interfaceQualifier");
    initBits &= ~INIT_BIT_INTERFACE_QUALIFIER;
    return this;
  }

  /**
   * Assigns a value to the {@link IInterfaceParameter#isOptional() isOptional} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IInterfaceParameter#isOptional() isOptional}.</em>
   * @param isOptional The value for isOptional
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInterfaceParameter setIsOptional(boolean isOptional) {
    this.isOptional = isOptional;
    optBits |= OPT_BIT_IS_OPTIONAL;
    return this;
  }

  /**
   * Assigns a value to the {@link IInterfaceParameter#getQualifier() qualifier} attribute.
   * @param qualifier The value for qualifier
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInterfaceParameter setQualifier(String qualifier) {
    this.qualifier = Objects.requireNonNull(qualifier, "qualifier");
    initBits &= ~INIT_BIT_QUALIFIER;
    return this;
  }

  /**
   * Adds one element to {@link IInterfaceParameter#getMetaTags() metaTags} list.
   * @param element The metaTags element
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInterfaceParameter addMetaTags(String element) {
    Objects.requireNonNull(element, "metaTags element");
    this.metaTags.add(element);
    return this;
  }

  /**
   * Adds elements to {@link IInterfaceParameter#getMetaTags() metaTags} list.
   * @param elements An array of metaTags elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableInterfaceParameter addMetaTags(String... elements) {
    for (String e : elements) {
      addMetaTags(e);
    }
    return this;
  }

  /**
   * Sets or replaces all elements for {@link IInterfaceParameter#getMetaTags() metaTags} list.
   * @param elements An iterable of metaTags elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInterfaceParameter setMetaTags(Iterable<String> elements) {
    this.metaTags.clear();
    addAllMetaTags(elements);
    return this;
  }

  /**
   * Adds elements to {@link IInterfaceParameter#getMetaTags() metaTags} list.
   * @param elements An iterable of metaTags elements
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInterfaceParameter addAllMetaTags(Iterable<String> elements) {
    for (String e : elements) {
      addMetaTags(e);
    }
    return this;
  }

  /**
   * Assigns a value to the {@link IInterfaceParameter#getSimpleName() simpleName} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link IInterfaceParameter#getSimpleName() simpleName}.</em>
   * @param simpleName The value for simpleName
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public MutableInterfaceParameter setSimpleName(String simpleName) {
    this.simpleName = Objects.requireNonNull(simpleName, "simpleName");
    return this;
  }

  /**
   * Returns {@code true} if the required attribute {@link IInterfaceParameter#getInterfaceQualifier() interfaceQualifier} is set.
   * @return {@code true} if set
   */
  public final boolean interfaceQualifierIsSet() {
    return (initBits & INIT_BIT_INTERFACE_QUALIFIER) == 0;
  }

  /**
   * Returns {@code true} if the required attribute {@link IInterfaceParameter#getQualifier() qualifier} is set.
   * @return {@code true} if set
   */
  public final boolean qualifierIsSet() {
    return (initBits & INIT_BIT_QUALIFIER) == 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IInterfaceParameter#isOptional() isOptional} is set.
   * @return {@code true} if set
   */
  public final boolean isOptionalIsSet() {
    return (optBits & OPT_BIT_IS_OPTIONAL) != 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link IInterfaceParameter#getSimpleName() simpleName} is set.
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
  public final MutableInterfaceParameter unsetInterfaceQualifier() {
    initBits |= INIT_BIT_INTERFACE_QUALIFIER;
    interfaceQualifier = null;
    return this;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableInterfaceParameter unsetQualifier() {
    initBits |= INIT_BIT_QUALIFIER;
    qualifier = null;
    return this;
  }
  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  @CanIgnoreReturnValue
  public final MutableInterfaceParameter unsetIsOptional() {
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
    if (!interfaceQualifierIsSet()) attributes.add("interfaceQualifier");
    if (!qualifierIsSet()) attributes.add("qualifier");
    return "InterfaceParameter is not initialized, some of the required attributes are not set " + attributes;
  }

  /**
   * Converts to {@link InterfaceParameter InterfaceParameter}.
   * @return An immutable instance of InterfaceParameter
   */
  public final InterfaceParameter toImmutable() {
    checkRequiredAttributes();
    return InterfaceParameter.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code MutableInterfaceParameter} that have equal attribute values.
   * An uninitialized instance is equal only to itself.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    if (!(another instanceof MutableInterfaceParameter)) return false;
    MutableInterfaceParameter other = (MutableInterfaceParameter) another;
    if (!isInitialized() || !other.isInitialized()) {
      return false;
    }
    return equalTo(other);
  }

  private boolean equalTo(MutableInterfaceParameter another) {
    boolean isOptional = isOptional();
    return interfaceQualifier.equals(another.interfaceQualifier)
        && isOptional == another.isOptional()
        && qualifier.equals(another.qualifier);
  }

  /**
   * Computes a hash code from attributes: {@code interfaceQualifier}, {@code isOptional}, {@code qualifier}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + interfaceQualifier.hashCode();
    boolean isOptional = isOptional();
    h += (h << 5) + Booleans.hashCode(isOptional);
    h += (h << 5) + qualifier.hashCode();
    return h;
  }

  /**
   * Generates a string representation of this {@code IInterfaceParameter}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("MutableInterfaceParameter")
        .add("interfaceQualifier", interfaceQualifierIsSet() ? getInterfaceQualifier() : "?")
        .add("isOptional", isOptional())
        .add("qualifier", qualifierIsSet() ? getQualifier() : "?")
        .toString();
  }
}
